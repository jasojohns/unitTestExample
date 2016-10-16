package example;

import java.util.List;
import java.util.TimeZone;

import org.joda.time.Instant;

import original.DateUtils;




public class TC_YearlyEvent extends TC_MonthlyEvent {

	public TC_YearlyEvent() {

	}

	public void testYearlyEndsAfter3Occurrences() {
		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setRecurrenceOnDayOfMonth(1);
		yearlyEvent.setOccuranceLimit(new Integer(3));
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JANUARY);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2020);


		yearlyEvent.setStartRecurrence(recurrenceStart);
		yearlyEvent.setEndRecurrence(recurenceEnd);
		List<InstanceExecution> instances = yearlyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 3);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 1, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 1, 2018)));

	}
}