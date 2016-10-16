package example;

import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.Instant;

import original.DateUtils;

public class TC_WeeklyEvent extends LegacyEventTestCase {

	public TC_WeeklyEvent() {

	}

	public void testWeeklyFromFirstMondayInJan() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		weeklyEvent.setDays(days);

		Instant jan4th2016 = getInstantAt(1, 4, 2016);
		Instant jan5th2016 = getInstantAt(1, 5, 2016);
		Instant firstOfMonth = getInstantAt(1, 1, 2016);
		Instant endOfMonth = getInstantAt(1, 31, 2016);


		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(endOfMonth);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 4);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), jan4th2016));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 25, 2016)));

		weeklyEvent.setStartRecurrence(jan4th2016);
		weeklyEvent.setEndRecurrence(endOfMonth);
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 4);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), jan4th2016));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 25, 2016)));

		weeklyEvent.setStartRecurrence(jan5th2016);
		weeklyEvent.setEndRecurrence(endOfMonth);
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 3);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 25, 2016)));


	}


	public void testWeeklyEveryOtherMonday() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(2);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		weeklyEvent.setDays(days);

		Instant jan4th2016 = getInstantAt(1, 4, 2016);
		Instant jan5th2016 = getInstantAt(1, 5, 2016);
		Instant firstOfMonth = getInstantAt(1, 1, 2016);
		Instant endOfMonth = getInstantAt(1, 31, 2016);


		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(endOfMonth);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), jan4th2016));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 18, 2016)));

		weeklyEvent.setStartRecurrence(jan4th2016);
		weeklyEvent.setEndRecurrence(endOfMonth);
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), jan4th2016));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 18, 2016)));

		weeklyEvent.setStartRecurrence(jan5th2016);
		weeklyEvent.setEndRecurrence(endOfMonth);
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 2);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 25, 2016)));

		//lets span over the feburary leap...the 29th should be included in this list...
		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(getInstantAt(3, 1, 2016));
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, getInstantAt(3, 1, 2016), TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(2, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(2, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(2, 29, 2016)));


	}

	public void testWeeklyMondayWednesdayFridayEveryWeek() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		weeklyEvent.setDays(days);

		Instant firstOfMonth = getInstantAt(1, 1, 2016);
		Instant endOfMonth = getInstantAt(1, 31, 2016);


		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(endOfMonth);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 13);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(1, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(1, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(1, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(1, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(1, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(1, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(12).getExecutionDatetime(), getInstantAt(1, 29, 2016)));

		weeklyEvent.setStartRecurrence(getInstantAt(1, 5, 2016));
		weeklyEvent.setEndRecurrence(endOfMonth);
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 11);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(1, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(1, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(1, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(1, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(1, 29, 2016)));


	}

	public void testWeeklyMondayWednesdayFridayEveryOtherWeek() {
		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(2);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		weeklyEvent.setDays(days);

		Instant firstOfMonth = getInstantAt(1, 1, 2016);
		Instant endOfMonth = getInstantAt(1, 31, 2016);


		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(endOfMonth);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 7);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(1, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(1, 29, 2016)));

		weeklyEvent.setStartRecurrence(getInstantAt(1, 5, 2016));
		weeklyEvent.setEndRecurrence(endOfMonth);
		instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 5);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 22, 2016)));


	}

	public void testWeeklyEveryWeekDayEveryWeek() {

		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.TUESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.THURSDAY);
		days.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SATURDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SUNDAY);
		weeklyEvent.setDays(days);

		Instant firstOfMonth = getInstantAt(1, 1, 2016);
		Instant endOfMonth = getInstantAt(1, 31, 2016);


		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(endOfMonth);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 31);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 5, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(1, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(1, 7, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(1, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(1, 9, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(1, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(1, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(1, 12, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(12).getExecutionDatetime(), getInstantAt(1, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(13).getExecutionDatetime(), getInstantAt(1, 14, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(14).getExecutionDatetime(), getInstantAt(1, 15, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(15).getExecutionDatetime(), getInstantAt(1, 16, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(16).getExecutionDatetime(), getInstantAt(1, 17, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(17).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(18).getExecutionDatetime(), getInstantAt(1, 19, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(19).getExecutionDatetime(), getInstantAt(1, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(20).getExecutionDatetime(), getInstantAt(1, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(21).getExecutionDatetime(), getInstantAt(1, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(22).getExecutionDatetime(), getInstantAt(1, 23, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(23).getExecutionDatetime(), getInstantAt(1, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(24).getExecutionDatetime(), getInstantAt(1, 25, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(25).getExecutionDatetime(), getInstantAt(1, 26, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(26).getExecutionDatetime(), getInstantAt(1, 27, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(27).getExecutionDatetime(), getInstantAt(1, 28, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(28).getExecutionDatetime(), getInstantAt(1, 29, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(29).getExecutionDatetime(), getInstantAt(1, 30, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(30).getExecutionDatetime(), getInstantAt(1, 31, 2016)));


	}

	public void testWeeklyEveryWeekDayEveryWeekLimitOf10() {

		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);
		weeklyEvent.setOccuranceLimit(new Integer(10));

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.TUESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.THURSDAY);
		days.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SATURDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SUNDAY);
		weeklyEvent.setDays(days);

		Instant firstOfMonth = getInstantAt(1, 1, 2016);
		Instant endOfMonth = getInstantAt(1, 31, 2016);


		weeklyEvent.setStartRecurrence(firstOfMonth);
		weeklyEvent.setEndRecurrence(endOfMonth);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(firstOfMonth, endOfMonth, TimeZone.getDefault());
		assertEquals(instances.size(), 10);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 4, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 5, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(1, 6, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(1, 7, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(1, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(1, 9, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(1, 10, 2016)));


	}

	public void testWeeklyEveryWeekDayEvery3rdWeek() {

		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(3);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.TUESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.THURSDAY);
		days.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SATURDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SUNDAY);
		weeklyEvent.setDays(days);

		Instant startRecurrence = getInstantAt(1, 1, 2016);
		Instant endRecurrence = getInstantAt(2, 29, 2016);


		weeklyEvent.setStartRecurrence(startRecurrence);
		weeklyEvent.setEndRecurrence(endRecurrence);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(startRecurrence, endRecurrence, TimeZone.getDefault());
		assertEquals(instances.size(), 18);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(1, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(1, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(1, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(1, 18, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(1, 19, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(1, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(1, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(1, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(1, 23, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(1, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(2, 8, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(2, 9, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(12).getExecutionDatetime(), getInstantAt(2, 10, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(13).getExecutionDatetime(), getInstantAt(2, 11, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(14).getExecutionDatetime(), getInstantAt(2, 12, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(15).getExecutionDatetime(), getInstantAt(2, 13, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(16).getExecutionDatetime(), getInstantAt(2, 14, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(17).getExecutionDatetime(), getInstantAt(2, 29, 2016)));

	}

	public void testWeeklyEveryWeekDayEvery3rdWeekOverYearBoundary() {

		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(3);

		HashSet<RecurringScheduleDayOfWeekEnum> days = new HashSet<RecurringScheduleDayOfWeekEnum>();
		days.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		days.add(RecurringScheduleDayOfWeekEnum.TUESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		days.add(RecurringScheduleDayOfWeekEnum.THURSDAY);
		days.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SATURDAY);
		days.add(RecurringScheduleDayOfWeekEnum.SUNDAY);
		weeklyEvent.setDays(days);

		Instant startRecurrence = getInstantAt(12, 1, 2016);
		Instant endRecurrence = getInstantAt(1, 31, 2017);


		weeklyEvent.setStartRecurrence(startRecurrence);
		weeklyEvent.setEndRecurrence(endRecurrence);
		List<InstanceExecution> instances = weeklyEvent.getScheduledInstancesForTimeframe(startRecurrence, endRecurrence, TimeZone.getDefault());
		assertEquals(instances.size(), 20);
		assertTrue(DateUtils.safeEquals(instances.get(0).getExecutionDatetime(), getInstantAt(12, 1, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(1).getExecutionDatetime(), getInstantAt(12, 2, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(2).getExecutionDatetime(), getInstantAt(12, 3, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(3).getExecutionDatetime(), getInstantAt(12, 4, 2016)));

		assertTrue(DateUtils.safeEquals(instances.get(4).getExecutionDatetime(), getInstantAt(12, 19, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(5).getExecutionDatetime(), getInstantAt(12, 20, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(6).getExecutionDatetime(), getInstantAt(12, 21, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(7).getExecutionDatetime(), getInstantAt(12, 22, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(8).getExecutionDatetime(), getInstantAt(12, 23, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(9).getExecutionDatetime(), getInstantAt(12, 24, 2016)));
		assertTrue(DateUtils.safeEquals(instances.get(10).getExecutionDatetime(), getInstantAt(12, 25, 2016)));

		assertTrue(DateUtils.safeEquals(instances.get(11).getExecutionDatetime(), getInstantAt(1, 9, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(12).getExecutionDatetime(), getInstantAt(1, 10, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(13).getExecutionDatetime(), getInstantAt(1, 11, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(14).getExecutionDatetime(), getInstantAt(1, 12, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(15).getExecutionDatetime(), getInstantAt(1, 13, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(16).getExecutionDatetime(), getInstantAt(1, 14, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(17).getExecutionDatetime(), getInstantAt(1, 15, 2017)));

		assertTrue(DateUtils.safeEquals(instances.get(18).getExecutionDatetime(), getInstantAt(1, 30, 2017)));
		assertTrue(DateUtils.safeEquals(instances.get(19).getExecutionDatetime(), getInstantAt(1, 31, 2017)));

	}

}
