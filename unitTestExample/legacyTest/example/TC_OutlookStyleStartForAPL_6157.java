package example;

import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.Instant;

import original.DateUtils;

//Test casees for APL-6157 fix...
public class TC_OutlookStyleStartForAPL_6157 extends LegacyEventTestCase {

	public TC_OutlookStyleStartForAPL_6157() {
	}

	//DAILY - Daily recurrences don't need to be fixed or tested as there is no directive in the configuration
	//that dictates that the start can be set earlier in the time unit of a day...

	//WEEKLY - no code was changed, testing out specific scenarios to ensure current code is working properly...
	public void testWeeklyOnMondayOutlookStyleStartForAPL_6157Fix() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);
		weeklyEvent.setOccuranceLimit(new Integer(5));

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		weeklyEvent.setDays(days);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2016);

		Instant recurrenceStart = getInstantAt(3, 9, 2016);
		Instant recurenceEnd = contractEnd;


		weeklyEvent.setStartRecurrence(recurrenceStart);
		weeklyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(3, 14, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(3, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 28, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(4, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(4, 11, 2016)));


	}

	public void testWeeklyOnThursdayOutlookStyleStartForAPL_6157Fix() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);
		weeklyEvent.setOccuranceLimit(new Integer(5));

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.THURSDAY);
		weeklyEvent.setDays(days);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2016);

		Instant recurrenceStart = getInstantAt(3, 9, 2016);
		Instant recurenceEnd = contractEnd;


		weeklyEvent.setStartRecurrence(recurrenceStart);
		weeklyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(3, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(3, 17, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(3, 31, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(4, 7, 2016)));

	}

	public void testWeeklyOnMondayAndThursdayOutlookStyleStartForAPL_6157Fix() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);
		weeklyEvent.setOccuranceLimit(new Integer(5));

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.THURSDAY);
		weeklyEvent.setDays(days);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2016);

		Instant recurrenceStart = getInstantAt(3, 9, 2016);
		Instant recurenceEnd = contractEnd;


		weeklyEvent.setStartRecurrence(recurrenceStart);
		weeklyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(3, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(3, 14, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(3, 17, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(3, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(3, 24, 2016)));

	}

	//MONTHLY - Code was updated, testing to ensure proper functionality...
	public void testMonthlyDayOfWeekOutlookStyleStartForAPL_6157Fix() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(4);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.DAY);

		Instant contractStart = getInstantAt(1, 1, 2015);
		Instant contractEnd = getInstantAt(12, 31, 2015);

		Instant recurrenceStart = getInstantAt(2, 5, 2015);
		Instant recurenceEnd = contractEnd;


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(6, 4, 2015)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(10, 4, 2015)));

	}

	public void testMonthlyWeekDayOutlookStyleStartForAPL_6157Fix() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(4);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.SECOND, RecurringScheduleExtendedDayOfWeekEnum.WEEK_DAY);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2016);

		Instant recurrenceStart = getInstantAt(3, 4, 2016);
		Instant recurenceEnd = contractEnd;

		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(7, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(11, 2, 2016)));

	}

	public void testMonthlySpecificDayOutlookStyleStartForAPL_6157Fix() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(4);
		monthlyEvent.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FIRST, RecurringScheduleExtendedDayOfWeekEnum.WEDNESDAY);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2016);

		Instant recurrenceStart = getInstantAt(3, 4, 2016);
		Instant recurenceEnd = contractEnd;

		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(7, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(11, 2, 2016)));

	}

	public void testMonthlyDayOfMonthOutlookStyleStartForAPL_6157Fix() {
		MonthlyRecurringScheduledEvent monthlyEvent = new MonthlyRecurringScheduledEvent();
		monthlyEvent.setRepeatInterval(4);
		monthlyEvent.setRecurrenceOnDayOfMonth(3);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2016);

		Instant recurrenceStart = getInstantAt(3, 4, 2016);
		Instant recurenceEnd = contractEnd;


		monthlyEvent.setStartRecurrence(recurrenceStart);
		monthlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = monthlyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(7, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(11, 3, 2016)));

	}

	//YEARLY - For due diligence we will test yearly, however yearly is just a special case of the montly code where the skip unit is a full calendar year...
	public void testYearlyInJanuaryOutlookStyleStartForAPL_6157Fix() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setRecurrenceOnDayOfMonth(1);
		yearlyEvent.setOccuranceLimit(new Integer(3));
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JANUARY);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2020);

		Instant recurrenceStart = getInstantAt(3, 4, 2016);
		Instant recurenceEnd = contractEnd;


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 3);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 1, 2018)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 1, 2019)));

	}

	public void testYearlyInAprilOutlookStyleStartForAPL_6157Fix() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setRecurrenceOnDayOfMonth(1);
		yearlyEvent.setOccuranceLimit(new Integer(3));
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.APRIL);

		Instant contractStart = getInstantAt(1, 1, 2016);
		Instant contractEnd = getInstantAt(12, 31, 2020);

		Instant recurrenceStart = getInstantAt(3, 4, 2016);
		Instant recurenceEnd = contractEnd;


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(contractStart, contractEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 3);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(4, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(4, 1, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(4, 1, 2018)));

	}

}
