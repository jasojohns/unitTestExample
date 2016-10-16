package example;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

public abstract class RecurringScheduledEvent extends ScheduledEvent {

	protected int repeatInterval;
	protected Integer occuranceLimit;

	protected Instant startRecurrence;
	protected Instant endRecurrence;

	public abstract Instant calculateNextRun(DateTimeZone timezone, Instant previousRun, int previousIteration);

	public Instant getStartRecurrence() {
		return startRecurrence;
	}

	public void setStartRecurrence(Instant startRecurrence) {
		this.startRecurrence = startRecurrence;
	}

	public Instant getEndRecurrence() {
		return endRecurrence;
	}

	public void setEndRecurrence(Instant endRecurrence) {
		this.endRecurrence = endRecurrence;
	}

	/**
	 * @return the repeatInterval
	 */
	@Override
	public int getRepeatInterval() {
		return repeatInterval;
	}

	/**
	 * @param repeatInterval the repeatInterval to set
	 */
	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public Integer getOccuranceLimit() {
		return occuranceLimit;
	}

	public void setOccuranceLimit(Integer occuranceLimit) {
		this.occuranceLimit = occuranceLimit;
	}


	@Override
	public List<InstanceExecution> getScheduledInstancesForTimeframe(Instant filterFrom, Instant filterTo, TimeZone defaultTimezone) {

		//cannot show recurrence instances if we don't know when they start, and lets not go on infinitly!
		if (startRecurrence == null) {
			throw new IllegalStateException("The start date of the event has not been specified.");
		}

		if (occuranceLimit == null && endRecurrence == null) {
			throw new IllegalStateException("The event must be limited by a specified number of occurences or by a specific end date.");
		}

		DateTimeZone appliedTimezone = getAppliedTimezone(defaultTimezone);

		Instant nextRun = calculateNextRun(appliedTimezone,  null, 0);

		DateTime fromDateTime = filterFrom.toDateTime(appliedTimezone);
		DateTime toDateTime = filterTo.toDateTime(appliedTimezone);

		List<InstanceExecution> instances = new ArrayList<InstanceExecution>();

		int index = 1;
		while(nextRun != null && safeIsBeforeOrEquals(nextRun.toDateTime(appliedTimezone), toDateTime)) {
			if (safeIsBetween(nextRun.toDateTime(appliedTimezone), fromDateTime, toDateTime)) {
				instances.add(new InstanceExecution(index, nextRun));
			}
			nextRun = calculateNextRun(appliedTimezone, nextRun, index);
			index++;
		}

		return instances;
	}
}
