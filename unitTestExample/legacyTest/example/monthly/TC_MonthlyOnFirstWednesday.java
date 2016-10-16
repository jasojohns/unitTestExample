package example.monthly;

import java.util.List;
import java.util.TimeZone;

import org.joda.time.Instant;

import original.DateUtils;
import example.InstanceExecution;
import example.MonthlyRecurringScheduledEvent;
import example.RecurringScheduleExtendedDayOfWeekEnum;
import example.RecurringScheduleWeekOfMonthEnum;
import example.TC_MonthlyEvent;

public class TC_MonthlyOnFirstWednesday extends TC_MonthlyEvent {

	public TC_MonthlyOnFirstWednesday() {
	}

	//Test case for APL-6157
	public void testMonthlyOnFirstWednesday() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setOccuranceLimit(new Integer(6));
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(5, 13, 2015);
		Instant recurenceEnd = getInstantAt(12, 31, 2017);

		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 6);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(6, 3, 2015)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(7, 1, 2015)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(8, 5, 2015)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(9, 2, 2015)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(10, 7, 2015)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(11, 4, 2015)));

	}



}
