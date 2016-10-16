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

public class TC_MonthlyEventDayOfWeek extends TC_MonthlyEvent {

	public TC_MonthlyEventDayOfWeek() {
	}

	/*
	 * Technically the UI won't be able to be configured this way, but there are no restrictions to the class to stop this from happening,
	 * so a series will be generated that makes sense for the configuration even if it doesn't make sense in practical use.
	 */
	public void testMonthlyFirstDayOfFirstWeekOfMonthEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
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
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 1, 2016)));


	}

	/*
	 * Technically the UI won't be able to be configured this way, but there are no restrictions to the class to stop this from happening,
	 * so a series will be generated that makes sense for the configuration even if it doesn't make sense in practical use.
	 */
	public void testMonthlySecondDayOfFirstWeekOfMonthEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 2, 2016)));


	}

	/*
	 * Technically the UI won't be able to be configured this way, but there are no restrictions to the class to stop this from happening,
	 * so a series will be generated that makes sense for the configuration even if it doesn't make sense in practical use.
	 */
	public void testMonthlyThirdDayOfFirstWeekOfMonthEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.THIRD, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 3, 2016)));


	}


	/*
	 * Technically the UI won't be able to be configured this way, but there are no restrictions to the class to stop this from happening,
	 * so a series will be generated that makes sense for the configuration even if it doesn't make sense in practical use.
	 */
	public void testMonthlyForthDayOfFirstWeekOfMonthEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 4, 2016)));


	}

	public void testMonthlyLastDayOfFirstWeekOfMonthEveryMonth() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(1);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 12);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(5, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(6, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(7, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(8, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(9, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(10, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(11, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(12, 31, 2016)));


	}

}
