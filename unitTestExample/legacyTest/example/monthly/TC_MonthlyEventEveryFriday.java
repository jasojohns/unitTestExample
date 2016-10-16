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

public class TC_MonthlyEventEveryFriday extends TC_MonthlyEvent {

	public TC_MonthlyEventEveryFriday() {
	}


	public void testMonthlyFirstFridayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.FRIDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 5, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 5, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10,7, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 2, 2016)));


	}

	public void testMonthlySecondFridayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.FRIDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 12, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 12, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 9, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 14, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 9, 2016)));


	}


	public void testMonthlyThirdFridayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.THIRD, RecurringScheduleExtendedDayOfWeekEnum.FRIDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 19, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 17, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 19, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 16, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 16, 2016)));

	}

	public void testMonthlyFourthFridayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.FRIDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 23, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 28, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 23, 2016)));


	}

	public void testMonthlyLastFridayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.FRIDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 28, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 30, 2016)));


	}

}
