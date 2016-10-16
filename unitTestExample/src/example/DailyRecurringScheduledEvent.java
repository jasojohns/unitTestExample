package example;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

public class DailyRecurringScheduledEvent extends RecurringScheduledEvent {

	@Override
	public RecurringScheduleFrequencyEnum getScheduleFrequency() {
		return RecurringScheduleFrequencyEnum.DAILY;
	}

	@Override
	public boolean isRecurring() {
		return true;
	}

	@Override
	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * TC_DailyEvent to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	public Instant calculateNextRun(DateTimeZone timezone, Instant previousRun, int previousIteration) {

		//see if there is a limit on the number of occurrences, and if we've reached that limit with the previous run
		if (occuranceLimit != null && occuranceLimit.intValue() == previousIteration) {
			return null;
		}

		Instant nextRun = null;

		if (previousRun == null) {
			nextRun = startRecurrence.toDateTime(timezone).toInstant();
		} else {
			nextRun = previousRun.toDateTime(timezone).plusDays(repeatInterval).toInstant();
		}

		if (endRecurrence != null && nextRun.isAfter(endRecurrence)) {
			return null;
		}

		return nextRun;
	}

}
