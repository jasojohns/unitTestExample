package example.performance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.joda.time.Instant;

import example.DailyRecurringScheduledEvent;
import example.InstanceExecution;
import example.LegacyEventTestCase;
import example.MonthlyRecurringScheduledEvent;
import example.OneTimeScheduledEvent;
import example.RecurringScheduleDayOfWeekEnum;
import example.RecurringScheduleExtendedDayOfWeekEnum;
import example.RecurringScheduleMonthsEnum;
import example.RecurringScheduleWeekOfMonthEnum;
import example.RecurringScheduledEvent;
import example.ScheduledEvent;
import example.WeeklyRecurringScheduledEvent;
import example.YearlyRecurringScheduledEvent;

public class TC_ScheduledEventPerformance extends LegacyEventTestCase {

	public TC_ScheduledEventPerformance() {

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testRecurringConglomerationPerformance() {

		//Simulates a "report" of multiple obligations accross multiple contracts

		int testCases[] = {1, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 500, 1000, 2000, 5000, 10000};

		//int testCases[] = {1};

		System.out.println("# Contracts, Time (ms), Event Instances");



		for (int i = 0; i < testCases.length; i++) {

			Instant startRecurrence = Instant.now();
			Instant endRecurrence = Instant.now().toDateTime().plusYears(1).toInstant();

			Collection<ScheduledEvent> allEvents = new ArrayList<ScheduledEvent>();
			for (int j = 0; j < testCases[i]; j++) {
				allEvents.addAll(getScheduledEventsForContract(startRecurrence, endRecurrence));
			}

			long start = System.currentTimeMillis();
			List<TestObligationInstance> obligationInstances = new ArrayList<TestObligationInstance>();
			for (ScheduledEvent event : allEvents) {
				if (event.isRecurring()) {
					RecurringScheduledEvent recurringEvent = (RecurringScheduledEvent)event;
					recurringEvent.setStartRecurrence(startRecurrence);
					recurringEvent.setEndRecurrence(endRecurrence);
				}
				List<InstanceExecution> instances = event.getScheduledInstancesForTimeframe(startRecurrence, endRecurrence, TimeZone.getDefault());
				for (InstanceExecution instance : instances) {
					obligationInstances.add(new TestObligationInstance(instance));
				}
			}

			Collections.sort(
				obligationInstances,
				TestObligationInstanceDueDateComparator.INSTANCE);

			long end = System.currentTimeMillis();
			System.out.println(testCases[i] + ", " + (end - start) + ", " + obligationInstances.size());



		}

	}

	public Collection<ScheduledEvent> getScheduledEventsForContract(Instant recurrenceStart, Instant recurrenceEnd) {
		Collection<ScheduledEvent> events = new ArrayList<ScheduledEvent>();

		for (int i = 0; i < 5; i++) {
			OneTimeScheduledEvent oneTimeEvent = new OneTimeScheduledEvent();
			oneTimeEvent.setEventTime(recurrenceStart.toDateTime().plusDays((i+1)*10).toInstant());
			events.add(oneTimeEvent);
		}

		DailyRecurringScheduledEvent dailyEvent1 = new DailyRecurringScheduledEvent();
		dailyEvent1.setRepeatInterval(1);
		events.add(dailyEvent1);

		DailyRecurringScheduledEvent dailyEvent2 = new DailyRecurringScheduledEvent();
		dailyEvent2.setRepeatInterval(5);
		events.add(dailyEvent2);

		WeeklyRecurringScheduledEvent weeklyEvent = new WeeklyRecurringScheduledEvent();
		weeklyEvent.setRepeatInterval(1);
		Set<RecurringScheduleDayOfWeekEnum> daysOfWeek = new HashSet<RecurringScheduleDayOfWeekEnum>();
		daysOfWeek.add(RecurringScheduleDayOfWeekEnum.MONDAY);
		daysOfWeek.add(RecurringScheduleDayOfWeekEnum.WEDNESDAY);
		daysOfWeek.add(RecurringScheduleDayOfWeekEnum.FRIDAY);
		weeklyEvent.setDays(daysOfWeek);
		events.add(weeklyEvent);

		MonthlyRecurringScheduledEvent monthlyEvent1 = new MonthlyRecurringScheduledEvent();
		monthlyEvent1.setRepeatInterval(1);
		monthlyEvent1.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.FOURTH, RecurringScheduleExtendedDayOfWeekEnum.WEEKEND_DAY);
		events.add(monthlyEvent1);

		MonthlyRecurringScheduledEvent monthlyEvent2 = new MonthlyRecurringScheduledEvent();
		monthlyEvent2.setRepeatInterval(1);
		monthlyEvent2.setRecurrenceDay(RecurringScheduleWeekOfMonthEnum.LAST, RecurringScheduleExtendedDayOfWeekEnum.DAY);
		events.add(monthlyEvent2);

		YearlyRecurringScheduledEvent yearlyEvent = new YearlyRecurringScheduledEvent();
		yearlyEvent.setRepeatInterval(1);
		yearlyEvent.setMonthOfYear(RecurringScheduleMonthsEnum.JUNE);
		yearlyEvent.setRecurrenceOnDayOfMonth(15);
		events.add(yearlyEvent);

		return events;
	}

	/*
	public void testRecurringExpansionPerformance() {

		int testCases[] = {100, 1000, 10000, 20000, 40000, 60000, 80000, 100000};

		try {

			for (int i = 0; i < testCases.length; i++) {
				ScheduledEvent event = getDailyEvent(testCases[i]);

				Instant startRecurrence = Instant.now();
				Instant endRecurrence = Instant.now().toDateTime().plusYears(testCases[i]).toInstant();

				long start = System.currentTimeMillis();
				event.getScheduledInstancesForTimeframe(startRecurrence, endRecurrence, TimeZone.getDefault());
				long end = System.currentTimeMillis();
				System.out.println(testCases[i] + ", " + (end - start));
			}

		} catch (JSONException e) {
			e.printStackTrace();
			fail();
		}
	}
	*/

	public ScheduledEvent getDailyEvent(int years) {
		DailyRecurringScheduledEvent dailyEvent = new DailyRecurringScheduledEvent();
		dailyEvent.setRepeatInterval(1);

		Instant startRecurrence = Instant.now();
		Instant endRecurrence = Instant.now().toDateTime().plusYears(years).toInstant();

		dailyEvent.setStartRecurrence(startRecurrence);
		dailyEvent.setEndRecurrence(endRecurrence);

		return dailyEvent;
	}
}
