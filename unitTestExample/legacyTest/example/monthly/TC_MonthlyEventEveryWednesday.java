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

public class TC_MonthlyEventEveryWednesday extends TC_MonthlyEvent {

	public TC_MonthlyEventEveryWednesday() {
	}


	public void testMonthlyFirstWednesdayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 7, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 5, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 7, 2016)));

	}

	public void testMonthlySecondWednesdayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 9, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 14, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 12, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 9, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 14, 2016)));

	}


	public void testMonthlyThirdWednesdayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.THIRD, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 17, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 16, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 17, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 19, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 16, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 21, 2016)));

	}

	public void testMonthlyFourthWednesdayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 23, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 28, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 23, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 28, 2016)));

	}

	public void testMonthlyLastWednesdayOfEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 28, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 28, 2016)));

	}

}
