package example.yearly;

import java.util.List;
import java.util.TimeZone;

import org.joda.time.Instant;

import original.DateUtils;
import example.InstanceExecution;
import example.RecurringScheduleExtendedDayOfWeekEnum;
import example.RecurringScheduleMonthsEnum;
import example.RecurringScheduleWeekOfMonthEnum;
import example.TC_YearlyEvent;
import example.YearlyRecurringScheduledEvent;

public class TC_YearlyEventWeekDayOfWeek extends TC_YearlyEvent {

	public TC_YearlyEventWeekDayOfWeek() {

	}

	public void testYearlyEventOnFirstWeekdayOfAprilFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.APRIL);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(4, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(4, 3, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(4, 2, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 1, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(4, 1, 2020)));

	}

	public void testYearlyEventOnSecondWeekDayOfDecemberFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.DECEMBER);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(12, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(12, 4, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(12, 4, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(12, 3, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(12, 2, 2020)));

	}

	public void testYearlyEventOnThirdWeekDayOfAugustFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.AUGUST);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.THIRD, RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(8, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(8, 3, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(8, 3, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(8, 5, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(8, 5, 2020)));

	}

	public void testYearlyEventOnFourthWeekDayOfAprilFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.APRIL);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(4, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(4, 6, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(4, 5, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 4, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(4, 6, 2020)));

	}


	public void testYearlyEventOnLastWeekDayOfMarchFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.MARCH);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(3, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(3, 31, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 30, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(3, 29, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(3, 31, 2020)));

	}


}
