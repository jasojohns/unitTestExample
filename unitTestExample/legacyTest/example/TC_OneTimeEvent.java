package example;

import java.util.List;
import java.util.TimeZone;

import org.joda.time.Instant;

public class TC_OneTimeEvent extends LegacyEventTestCase {

	public TC_OneTimeEvent() {
	}

	public void testOneTimeEvent() {
		OneTimeScheduledEvent oneTimeEvent = new OneTimeScheduledEvent();

		//repeat interval for one time event should always be 0
		assertEquals(oneTimeEvent.getRepeatInterval(), 0);


		Instant eventTime = getInstantAt(1, 15, 2016);

		Instant filterStart = getInstantAt(1, 1, 2016);
		Instant filterEnd = getInstantAt(1, 31, 2016);

		oneTimeEvent.setEventTime(eventTime);


		List<InstanceExecution> instancesIncluded = oneTimeEvent.getScheduledInstancesForTimeframe(filterStart, filterEnd, TimeZone.getDefault());
		assertEquals(instancesIncluded.size(), 1);

		List<InstanceExecution> instancesBefore =
			oneTimeEvent.getScheduledInstancesForTimeframe(getInstantAt(1, 16, 2016), filterEnd, TimeZone.getDefault());
		assertEquals(instancesBefore.size(), 0);

		List<InstanceExecution> instancesOnStart = oneTimeEvent.getScheduledInstancesForTimeframe(getInstantAt(1, 15, 2016), filterEnd, TimeZone.getDefault());
		assertEquals(instancesOnStart.size(), 1);

		List<InstanceExecution> instancesAfter =
			oneTimeEvent.getScheduledInstancesForTimeframe(filterStart, getInstantAt(1, 14, 2016), TimeZone.getDefault());
		assertEquals(instancesAfter.size(), 0);

		List<InstanceExecution> instancesOnAfter= oneTimeEvent.getScheduledInstancesForTimeframe(filterStart, getInstantAt(1, 15, 2016), TimeZone.getDefault());
		assertEquals(instancesOnAfter.size(), 1);


	}

}
