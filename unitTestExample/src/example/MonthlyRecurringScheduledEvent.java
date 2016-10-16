package example;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.MutableDateTime;

public class MonthlyRecurringScheduledEvent extends RecurringScheduledEvent {

	protected boolean recurrsOnDayOfMonth;
	protected Integer dayOfMonth;
	protected RecurringScheduleWeekOfMonthEnum weekOfMonth;
	protected RecurringScheduleExtendedDayOfWeekEnum dayOfWeek;

	protected enum DayOfWeekType {
		WEEKDAY,
		WEEKEND
	}

	@Override
	public RecurringScheduleFrequencyEnum getScheduleFrequency() {
		return RecurringScheduleFrequencyEnum.MONTHLY;
	}

	/**
	 * @return the dayNumber
	 */
	public Integer getDayNumber() {
		return dayOfMonth;
	}

	/**
	 * @return the monthDayType
	 */
	public RecurringScheduleWeekOfMonthEnum getMonthDayType() {
		return weekOfMonth;
	}

	/**
	 * @return the dayOfWeek
	 */
	public RecurringScheduleExtendedDayOfWeekEnum getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @return boolean recurrsOnDayOfMonth
	 */
	public boolean recurrsOnDayOfMonth() {
		return recurrsOnDayOfMonth;
	}

	public void setRecurrenceOnDayOfMonth(int dayNumber) {
		this.recurrsOnDayOfMonth = true;
		this.dayOfMonth = Integer.valueOf(dayNumber);
		this.weekOfMonth = null;
	}

	public void setRecurrenceDay(RecurringScheduleWeekOfMonthEnum week, RecurringScheduleExtendedDayOfWeekEnum day) {
		this.weekOfMonth = week;
		this.dayOfWeek = day;
		this.recurrsOnDayOfMonth = false;
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
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * TC_MonthlyEvent* to ensure current functionality is not broken, and add
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
	 * com.sciquest.app.general.recurringschedule.TC_MonthlyEvent* to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	protected Instant getNextRunInstantFrom(Instant fromDate, boolean isBeginning, DateTimeZone timezone) {

		if (this.recurrsOnDayOfMonth) {
			return getNextDayOfMonthForRun(fromDate, isBeginning, timezone);
		} else {
			if (dayOfWeek == RecurringScheduleExtendedDayOfWeekEnum.DAY) {
				return getNextDayOfWeekOfMonthForRun(fromDate, isBeginning, timezone);
			} else if (dayOfWeek == RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY) {
				return getNextWeekdayOrWeekendDayOfMonthForRun(fromDate, isBeginning, timezone, DayOfWeekType.WEEKDAY);
			} else if (dayOfWeek == RecurringScheduleExtendedDayOfWeekEnum.WEEKEND_DAY) {
				return getNextWeekdayOrWeekendDayOfMonthForRun(fromDate, isBeginning, timezone, DayOfWeekType.WEEKEND);
			} else { //dayOfWeek == Specific day of week enum...
				return getNextSpecificDayOfWeekForRun(fromDate, isBeginning, timezone);
			}
		}
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_MonthlyEvent* to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 *
	 * Technically the UI won't be able to configure an ordinal value other than "Last" with the DAY option, but there are no
	 * restrictions to the class to stop this from happening, so a series will be generated that makes sense for the configuration
	 * even if it doesn't make sense in practical use.
	 */
	private Instant getNextDayOfWeekOfMonthForRun(Instant fromDate, boolean isBeginning, DateTimeZone timezone) {

		MutableDateTime nextRun = new MutableDateTime(fromDate);
		nextRun.setZone(timezone);

		if (isBeginning) {
			//assume this month unit is where we are starting from...
			setBeginningData(nextRun);
			nextRun.setDayOfMonth(getIndexOfDayFromWeekOfMonthEnum(nextRun.toDateTime()));

			//but the actual start should not be in the past, so go to the next unit...
			if (nextRun.isBefore(fromDate)) {
				return getNextDayOfWeekOfMonthForRun(nextRun.toInstant(), false, timezone);
			}
		} else {
			addTime(nextRun, getRepeatInterval());
			nextRun.setDayOfMonth(getIndexOfDayFromWeekOfMonthEnum(nextRun.toDateTime()));
		}

		return nextRun.toInstant();
	}

	protected void setBeginningData(MutableDateTime dateTime) {
		//nothing to do for montly...
	}

	protected void addTime(MutableDateTime dateTime, int numberOfUnits) {
		dateTime.addMonths(numberOfUnits);
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_MonthlyEvent* to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	private Instant getNextWeekdayOrWeekendDayOfMonthForRun(Instant fromDate, boolean isBeginning, DateTimeZone timezone, DayOfWeekType dayType) {

		MutableDateTime nextRun = new MutableDateTime(fromDate);
		nextRun.setZone(timezone);

		if (isBeginning) {
			//assume this month unit is where we are starting from...
			setBeginningData(nextRun);
			nextRun.setDayOfMonth(getWeekdayOrWeekendDayOfMonthAccordingToWeekOfMonthEnum(nextRun.toDateTime(timezone), dayType));

			//but the actual start should not be in the past, so go to the next unit...
			if (nextRun.isBefore(fromDate)) {
				return getNextWeekdayOrWeekendDayOfMonthForRun(nextRun.toInstant(), false, timezone, dayType);
			}

		} else {
			addTime(nextRun, getRepeatInterval());
			nextRun.setDayOfMonth(getWeekdayOrWeekendDayOfMonthAccordingToWeekOfMonthEnum(nextRun.toDateTime(timezone), dayType));
		}

		return nextRun.toInstant();
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_MonthlyEvent* to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	private int getWeekdayOrWeekendDayOfMonthAccordingToWeekOfMonthEnum(DateTime dateTime, DayOfWeekType dayType) {
		MutableDateTime monthInQuestion = new MutableDateTime(dateTime);
		monthInQuestion.setZone(dateTime.getZone());

		if (weekOfMonth == RecurringScheduleWeekOfMonthEnum.LAST) {
			monthInQuestion.setDayOfMonth(getLastDayOfMonth(dateTime));
			boolean weekDayFound = false;
			while(!weekDayFound) {
				if(!isTypeOfDay(monthInQuestion.getDayOfWeek(), dayType)) {
					monthInQuestion.addDays(-1);
				} else {
					weekDayFound = true;
				}
			}
		} else {
			monthInQuestion = new MutableDateTime(dateTime.withDayOfMonth(1));
			int daysToSkip = weekOfMonth.getCode();
			boolean weekDayFound = false;
			while(!weekDayFound) {
				if(isTypeOfDay(monthInQuestion.getDayOfWeek(), dayType)) {
					daysToSkip--;
				}

				if (daysToSkip == 0) {
					weekDayFound = true;
				} else {
					monthInQuestion.addDays(1);
				}
			}
		}

		return monthInQuestion.getDayOfMonth();
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_MonthlyEvent* to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	private Instant getNextSpecificDayOfWeekForRun(Instant fromDate, boolean isBeginning, DateTimeZone timezone) {

		MutableDateTime nextRun = new MutableDateTime(fromDate);
		nextRun.setZone(timezone);

		if (isBeginning) {
			//assume this month unit is where we are starting from...
			setBeginningData(nextRun);
			nextRun.setDayOfMonth(getOccurenceOfWeekdayForMonth(nextRun.toDateTime(timezone), dayOfWeek.getJodaDateTimeConstantForDayOfWeekEnum(), weekOfMonth));

			//but the actual start should not be in the past, so go to the next unit...
			if (nextRun.isBefore(fromDate)) {
				return getNextSpecificDayOfWeekForRun(nextRun.toInstant(), false, timezone);
			}

		} else {
			addTime(nextRun, getRepeatInterval());
			nextRun.setDayOfMonth(getOccurenceOfWeekdayForMonth(nextRun.toDateTime(timezone), dayOfWeek.getJodaDateTimeConstantForDayOfWeekEnum(), weekOfMonth));
		}

		return nextRun.toInstant();
	}

	protected boolean isTypeOfDay(int dayIndex, DayOfWeekType dayType) {

		boolean isWeekendDay = (dayIndex == DateTimeConstants.SATURDAY || dayIndex == DateTimeConstants.SUNDAY);

		if (dayType == DayOfWeekType.WEEKDAY) {
			return !isWeekendDay;
		}

		return isWeekendDay;
	}

	private int getOccurenceOfWeekdayForMonth(DateTime dateTime, int dayOfWeek, RecurringScheduleWeekOfMonthEnum weekOfMonth) {
		if (dayOfWeek < 1) {
			throw new IllegalStateException("The day of the week specified doesn't match a Joda day of week constant.");
		}

		MutableDateTime monthInQuestion = new MutableDateTime(dateTime);
		monthInQuestion.setZone(dateTime.getZone());

		if (weekOfMonth == RecurringScheduleWeekOfMonthEnum.LAST) {
			monthInQuestion.setDayOfMonth(getLastDayOfMonth(dateTime));
			boolean weekDayFound = false;
			while(!weekDayFound) {
				if(dayOfWeek == monthInQuestion.getDayOfWeek()) {
					weekDayFound = true;
				} else {
					monthInQuestion.addDays(-1);
				}
			}
		} else {
			monthInQuestion = new MutableDateTime(dateTime.withDayOfMonth(1));
			boolean weekDayFound = false;
			while(!weekDayFound) {
				if(dayOfWeek == monthInQuestion.getDayOfWeek()) {
					weekDayFound = true;
				} else {
					monthInQuestion.addDays(1);
				}
			}
			monthInQuestion.addWeeks(weekOfMonth.getCode()-1);
		}

		return monthInQuestion.getDayOfMonth();
	}

	/* Technically the UI won't be able to configure an ordinal value other than "Last" with the DAY option, but there are no
	 * restrictions to the class to stop this from happening, so a series will be generated that makes sense for the configuration
	 * even if it doesn't make sense in practical use.
	 */
	private int getIndexOfDayFromWeekOfMonthEnum(DateTime dateTime) {
		if (weekOfMonth == RecurringScheduleWeekOfMonthEnum.FIRST) {
			return 1;
		} else if (weekOfMonth == RecurringScheduleWeekOfMonthEnum.SECOND) {
			return 2;
		} else if (weekOfMonth == RecurringScheduleWeekOfMonthEnum.THIRD) {
			return 3;
		} else if (weekOfMonth == RecurringScheduleWeekOfMonthEnum.FOURTH) {
			return 4;
		} else /*(weekOfMonth == RecurringScheduleWeekOfMonthEnum.LAST)*/ {
			return getLastDayOfMonth(dateTime);
		}
	}

	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * com.sciquest.app.general.recurringschedule.TC_MonthlyEvent* to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	private Instant getNextDayOfMonthForRun(Instant fromDate, boolean isBeginning, DateTimeZone timezone) {

		MutableDateTime nextRun = new MutableDateTime(fromDate);
		nextRun.setZone(timezone);

		if (isBeginning) {
			//assume this month unit is where we are starting from...
			setBeginningData(nextRun);
			nextRun.setDayOfMonth(getDayOfMonthCorrectedForLastDoM(nextRun.toDateTime(), dayOfMonth.intValue()));

			//but the actual start should not be in the past, so go to the next unit...
			if (nextRun.isBefore(fromDate)) {
				return getNextDayOfMonthForRun(nextRun.toInstant(), false, timezone);
			}

		} else {
			//this will reliably move the month ahead even if the dayOfMonth exceeds the value for the new month, the dayOfMonth is automatically
			//set to the last day of the month...which is sorta what we want, but we ensure we have the right day of month further down...
			addTime(nextRun, getRepeatInterval());

			int toDayOfMonth = getDayOfMonthCorrectedForLastDoM(nextRun.toDateTime(), dayOfMonth.intValue());
			nextRun.setDayOfMonth(toDayOfMonth);

		}

		return nextRun.toInstant();
	}

	private int getLastDayOfMonth(DateTime dateTime) {
		return dateTime.dayOfMonth().withMaximumValue().getDayOfMonth();
	}

	private int getDayOfMonthCorrectedForLastDoM(DateTime dateTime, int desiredDayOfMonth) {
		int lastDayOfMonth = getLastDayOfMonth(dateTime);
		if (desiredDayOfMonth < lastDayOfMonth) {
			return desiredDayOfMonth;
		} else {
			return lastDayOfMonth;
		}
	}
}
