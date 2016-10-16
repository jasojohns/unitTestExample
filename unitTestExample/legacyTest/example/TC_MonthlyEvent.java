package example;

import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTimeConstants;
import org.joda.time.Instant;

import original.DateUtils;
import example.MonthlyRecurringScheduledEvent.DayOfWeekType;

public class TC_MonthlyEvent extends LegacyEventTestCase {

	public TC_MonthlyEvent() {
	}

	public void testWeekdayVsWeekend() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();

		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.MONDAY, DayOfWeekType.WEEKDAY));
		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.TUESDAY, DayOfWeekType.WEEKDAY));
		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.WEDNESDAY, DayOfWeekType.WEEKDAY));
		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.THURSDAY, DayOfWeekType.WEEKDAY));
		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.FRIDAY, DayOfWeekType.WEEKDAY));
		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.SATURDAY, DayOfWeekType.WEEKEND));
		assertTrue(monthlyEvent.isTypeOfDay(DateTimeConstants.SUNDAY, DayOfWeekType.WEEKEND));

	}

	public void testMonthlyEndsAfter10Occurrences() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceOnDayOfMonth(1);
		monthlyEvent.setOccuranceLimit(new Integer(10));

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 10);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 1, 2016)));

	}

}
