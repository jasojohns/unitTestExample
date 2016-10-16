package example;

import java.util.Set;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.MutableDateTime;

public class WeeklyRecurringScheduledEvent extends RecurringScheduledEvent {

	private Set<RecurringScheduleDayOfWeekEnum> days;
	private boolean[] daysSetArray = new boolean[7];

	@Override
	public RecurringScheduleFrequencyEnum getScheduleFrequency() {
		return RecurringScheduleFrequencyEnum.WEEKLY;
	}

	/**
	 * @return the days
	 */
	public Set<RecurringScheduleDayOfWeekEnum> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Set<RecurringScheduleDayOfWeekEnum> days) {
		this.days = days;
		daysSetArray = new boolean[7]; //reset all to false
		if (this.days != null && this.days.size() > 0) {
			for (RecurringScheduleDayOfWeekEnum dayOfWeek : this.days) {
				if (dayOfWeek == RecurringScheduleDayOfWeekEnum.SUNDAY) {
					daysSetArray[6] = true;
				} else {
					daysSetArray[dayOfWeek.getCode()-1] = true;
				}
			}
		}
	}

	@Override
	public boolean isRecurring() {
		return true;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see RecurringScheduledEvent#calculateNextRun(java.util.TimeZone, org.joda.time.Instant, int)
	 *
	 * DEVNOTE: If any logic is changed in this method or getNextRunInstantFrom() please run the unit tests in
	 * TC_WeeklyEvent to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	public Instant calculateNextRun(DateTimeZone timezone, Instant previousRun, int previousIteration) {

		//cannot show recurrence instances if we don't know when they start, and lets not go on infinitly!
		if (startRecurrence == null) {
			throw new IllegalStateException("The start date of the event has not been specified.");
		}

		if (occuranceLimit == null && endRecurrence == null) {
			throw new IllegalStateException("The event must be limited by a specified number of occurences or by a specific end date.");
		}

		if (days == null || days.size() == 0 || isDaysSetArrayAllFalse()) {
			throw new IllegalStateException("There must be at least one day of the week set for a weekly event to occur on.");
		}

		//see if there is a limit on the number of occurrences, and if we've reached that limit with the previous run
		if (occuranceLimit != null && occuranceLimit.intValue() == previousIteration) {
			return null;
		}

		Instant nextRun = null;

		if (previousRun == null) {
			nextRun = getNextRunInstantFrom(startRecurrence, true, timezone);
		} else {
			nextRun = getNextRunInstantFrom(previousRun, false, timezone);
		}

		if (endRecurrence != null && nextRun.isAfter(endRecurrence)) {
			return null;
		}

		return nextRun;
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_WeeklyEvent to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	private Instant getNextRunInstantFrom(Instant fromDate, boolean isBeginning, DateTimeZone timezone) {

		MutableDateTime nextRun = new MutableDateTime(fromDate);
		nextRun.setZone(timezone);

		int daysToAdd = 0;
		if (isBeginning) {
			int dayIndex = nextRun.getDayOfWeek()-1;
			while (!daysSetArray[dayIndex++]) {
				daysToAdd++;
				if (dayIndex == 7) {
					dayIndex = 0;
				}
			}
		} else {
			daysToAdd = 1;
			int dayIndex = incrementDayIndex(nextRun.getDayOfWeek()-1, nextRun);
			while (!daysSetArray[dayIndex]) {
				daysToAdd++;
				dayIndex = incrementDayIndex(dayIndex, nextRun);
			}
		}

		nextRun.addDays(daysToAdd);

		return nextRun.toInstant();
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_WeeklyEvent to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	private int incrementDayIndex(int dayIndex, MutableDateTime runTime) {
		int newIndex = dayIndex+1;
		if (newIndex == 7) {
			runTime.addWeeks(getRepeatInterval()-1);
			newIndex = 0;
		}

		return newIndex;
	}

	private boolean isDaysSetArrayAllFalse() {
		for (int i = 0; i < daysSetArray.length; i++) {
			if (daysSetArray[i]) {
				return false;
			}
		}

		return true;
	}
}
