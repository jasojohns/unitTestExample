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

public class TC_YearlyEventDayOfMonth extends TC_YearlyEvent {

	public TC_YearlyEventDayOfMonth() {

	}

	public void testYearlyEventOn1stOfJanuaryFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JANUARY);
		yearlyEvent.setRecurrenceOnDayOfMonth(1);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 1, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 1, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 1, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 1, 2020)));

	}

	public void testYearlyEvent31stOfFeburaryFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.FEBURARY);
		yearlyEvent.setRecurrenceOnDayOfMonth(31);

		Instant recurrenceStart = getInstantAt(2, 1, 2016);
		Instant recurenceEnd = getInstantAt(3, 1, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(2, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 28, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(2, 28, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(2, 28, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(2, 29, 2020)));

	}

	public void testYearlyEventOn31stOfJanuaryFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JANUARY);
		yearlyEvent.setRecurrenceOnDayOfMonth(31);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 31, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 31, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 31, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 31, 2020)));

	}

	public void testYearlyEventOnFirstOfMarchFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.MARCH);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(3, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(3, 1, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 1, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(3, 1, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(3, 1, 2020)));

	}

	public void testYearlyEventOnSecondDayOfNovemberFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.NOVEMBER);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(11, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(11, 2, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(11, 2, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(11, 2, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(11, 2, 2020)));

	}

	public void testYearlyEventOnThirdDayOfJulyFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JULY);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.THIRD, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(7, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(7, 3, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(7, 3, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(7, 3, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(7, 3, 2020)));

	}

	public void testYearlyEventOnFourthDayOfMarchFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.MARCH);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(3, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(3, 4, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 4, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(3, 4, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(3, 4, 2020)));

	}

	public void testYearlyEventOnLastDayOfFeburaryFor5Years() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.FEBURARY);
		yearlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant recurrenceStart = getInstantAt(2, 1, 2016);
		Instant recurenceEnd = getInstantAt(3, 1, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(2, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(2, 28, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(2, 28, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(2, 28, 2019)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(2, 29, 2020)));

	}


}
