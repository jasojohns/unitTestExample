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

public class TC_YearlyEventEveryWednesday extends TC_YearlyEvent {

	public TC_YearlyEventEveryWednesday() {

	}

	public void testYearlyEventOnFirstWednesdayDayOfAugustFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.AUGUST);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(8, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(8, 2, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(8, 1, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(8, 7, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(8, 5, 2020)));

	}

	public void testYearlyEventOnSecondWednesdayOfAprilFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.APRIL);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(4, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(4, 12, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(4, 11, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 10, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(4, 8, 2020)));

	}

	public void testYearlyEventOnThirdWednesdayDayOfDecemberFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.DECEMBER);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.THIRD, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(12, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(12, 20, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(12, 19, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(12, 18, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(12, 16, 2020)));


	}

	public void testYearlyEventOnFourthWednesdayDayOfAugustFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.AUGUST);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(8, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(8, 23, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(8, 22, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(8, 28, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(8, 26, 2020)));


	}

	public void testYearlyEventOnLastWednesdayOfJulyFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JULY);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(7, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(7, 26, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(7, 25, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(7, 31, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(7, 29, 2020)));


	}


}
