package example;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

public class OneTimeScheduledEvent extends ScheduledEvent {

	private Instant eventTime;

	@Override
	public RecurringScheduleFrequencyEnum getScheduleFrequency() {
		return RecurringScheduleFrequencyEnum.ONCE;
	}

	/**
	 * @return the eventTime
	 */
	public Instant getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime the eventTime to set
	 */
	public OneTimeScheduledEvent setEventTime(Instant eventTime) {
		this.eventTime = eventTime;
		return this;
	}

	@Override
	public boolean isRecurring() {
		return false;
	}

	@Override
	public int getRepeatInterval() {
		return 0;
	}

	@Override
	/*
	 * DEVNOTE: If any logic is changed in this method please run the unit tests in
	 * TC_OneTimeEvent to ensure current functionality is not broken, and add
	 * new test cases to excercise the new logic.
	 */
	public List<InstanceExecution> getScheduledInstancesForTimeframe(Instant filterFrom, Instant filterTo, TimeZone defaultTimezone) {
		List<InstanceExecution> instances = new ArrayList<InstanceExecution>();

		DateTimeZone appliedTimezone = getAppliedTimezone(defaultTimezone);

		Instant executionTime = getEventTime().toDateTime(appliedTimezone).toInstant();

		if (safeIsBetween(executionTime.toDateTime(appliedTimezone), filterFrom.toDateTime(appliedTimezone), filterTo.toDateTime(appliedTimezone))) {
			instances.add(new InstanceExecution(1, executionTime));
		}

		return instances;
	}

}
