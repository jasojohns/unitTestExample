package example;

import java.util.List;
import java.util.TimeZone;

import org.joda.time.Days;
import org.joda.time.Instant;

public class TC_DailyEvent extends LegacyEventTestCase {

	public TC_DailyEvent() {
	}

	public void testDailyEventFor1Year() {
		DailyRecurringScheduledEvent dailyEvent = new DailyRecurringScheduledEvent();
		dailyEvent.setRepeatInterval(1);

		Instant recurrenceStart = getInstantAt(1, 1, 2016);
		Instant recurenceEnd = getInstantAt(12, 31, 2016);

		dailyEvent.setStartRecurrence(recurrenceStart);
		dailyEvent.setEndRecurrence(recurenceEnd);

		List<InstanceExecution> instances = dailyEvent.getScheduledInstancesForTimeframe(recurrenceStart, recurenceEnd, TimeZone.getDefault());
		assertEquals(instances.size(), 366);

	}

	public void testDailyEventRepeatIntervalOf1NoLimit() {
		DailyRecurringScheduledEvent dailyEvent = new DailyRecurringScheduledEvent();
		dailyEvent.setRepeatInterval(1);
		dailyEvent.setStartRecurrence(Instant.now());
		dailyEvent.setEndRecurrence(Instant.now().toDateTime().plusDays(90).toInstant());

		int fifteenDays = 15;
		int thirtyDays = 30;

		Instant filterStart = Instant.now().toDateTime().minusDays(1).toInstant();
		Instant filterEnd15 = filterStart.toDateTime().plusDays(fifteenDays).toInstant();
		Instant filterEnd30 = filterStart.toDateTime().plusDays(thirtyDays).toInstant();


		List<InstanceExecution> instances = dailyEvent.getScheduledInstancesForTimeframe(filterStart, filterEnd15, TimeZone.getDefault());
		assertEquals(instances.size(), fifteenDays);

		instances = dailyEvent.getScheduledInstancesForTimeframe(filterStart, filterEnd30, TimeZone.getDefault());
		assertEquals(instances.size(), thirtyDays);

	}

	public void testDailyEventRepeatIntervalOf5NoLimit() {
		DailyRecurringScheduledEvent dailyEvent = new DailyRecurringScheduledEvent();
		dailyEvent.setRepeatInterval(5);
		dailyEvent.setStartRecurrence(Instant.now());
		dailyEvent.setEndRecurrence(Instant.now().toDateTime().plusDays(90).toInstant());

		int fifteenDays = 15;

		Instant filterStart = Instant.now().toDateTime().minusDays(1).toInstant();
		Instant filterEnd15 = filterStart.toDateTime().plusDays(fifteenDays).toInstant();

		try {
			List<InstanceExecution> instances = dailyEvent.getScheduledInstancesForTimeframe(filterStart, filterEnd15, TimeZone.getDefault());
			assertEquals(instances.size(), 3);

			//each of the 3 instances returned should be 5 days appart from each other
			InstanceExecution instance1 = null;
			InstanceExecution instance2 = null;
			InstanceExecution instance3 = null;

			for (InstanceExecution instance : instances) {
				if (instance.getIteration() == 1) {
					instance1 = instance;
				} else if (instance.getIteration() == 2) {
					instance2 = instance;
				} else if (instance.getIteration() == 3) {
					instance3 = instance;
				}
			}

			int differenceBetween1And2 =
				Days.daysBetween(
					instance1.getExecutionDatetime().toDateTime().withTimeAtStartOfDay(),
					instance2.getExecutionDatetime().toDateTime().withTimeAtStartOfDay()).getDays();
			assertEquals(differenceBetween1And2, 5);

			int differenceBetween2And3 =
				Days.daysBetween(
					instance2.getExecutionDatetime().toDateTime().withTimeAtStartOfDay(),
					instance3.getExecutionDatetime().toDateTime().withTimeAtStartOfDay()).getDays();
			assertEquals(differenceBetween2And3, 5);


		} catch (NullPointerException e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testDailyEvent1LimitTo10Occurrences() {
		DailyRecurringScheduledEvent dailyEvent = new DailyRecurringScheduledEvent();
		dailyEvent.setRepeatInterval(1);
		dailyEvent.setOccuranceLimit(new Integer(10));
		dailyEvent.setStartRecurrence(Instant.now());
		dailyEvent.setEndRecurrence(Instant.now().toDateTime().plusDays(90).toInstant());

		int fifteenDays = 15;

		Instant filterStart = Instant.now().toDateTime().minusDays(1).toInstant();
		Instant filterEnd15 = filterStart.toDateTime().plusDays(fifteenDays).toInstant();


		List<InstanceExecution> instances = dailyEvent.getScheduledInstancesForTimeframe(filterStart, filterEnd15, TimeZone.getDefault());
		assertEquals(instances.size(), 10);

	}

}
