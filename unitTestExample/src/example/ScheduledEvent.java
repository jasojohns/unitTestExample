package example;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

public abstract class ScheduledEvent {

	public abstract RecurringScheduleFrequencyEnum getScheduleFrequency();
	public abstract boolean isRecurring();
	public abstract int getRepeatInterval();

	public abstract List<InstanceExecution> getScheduledInstancesForTimeframe(Instant start, Instant end, TimeZone defaultTimezone);

	protected TimeZone timeZone;

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	protected DateTimeZone getAppliedTimezone(TimeZone defaultTimezone) {
		return DateTimeZone.forTimeZone(getTimeZone() != null ? getTimeZone() : defaultTimezone);
	}

	/**
	 * Checks if date 1 is before or equal to date2
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static final boolean safeIsBeforeOrEquals(DateTime date1, DateTime date2) {
		if (date1 != null && date2 != null) {
			return date1.isBefore(date2) || date1.isEqual(date2);
		}
		return false;
	}

	/**
	 * Checks if date1 is inclusively between date2 and date3
	 * @param date1
	 * @param date2
	 * @param date3
	 * @return boolean
	 */
	public static final boolean safeIsBetween(Date date1, Date date2, Date date3) {
		if (date1 != null && date2 != null && date3 != null) {
			return (date1.after(date2) || date1.equals(date2)) && (date1.before(date3) || date1.equals(date3));
		}
		return false;
	}

	/**
	 * Checks if date1 is inclusively between date2 and date3
	 * @param date1
	 * @param date2
	 * @param date3
	 * @return boolean
	 */
	public static final boolean safeIsBetween(DateTime date1, DateTime date2, DateTime date3) {
		if (date1 != null && date2 != null && date3 != null) {
			return (date1.isAfter(date2) || date1.equals(date2)) && (date1.isBefore(date3) || date1.equals(date3));
		}
		return false;
	}
}
