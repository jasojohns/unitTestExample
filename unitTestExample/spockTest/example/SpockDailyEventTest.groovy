package example

import java.util.List;
import java.util.TimeZone;

import org.joda.time.Days;
import org.joda.time.Instant

import spock.lang.*

class SpockDailyEventTest extends SpockEventTestCase {

	//2016 is good for testing leap year logic!
	static Instant jan1st2016 = getInstantAt(1, 1, 2016)
	static Instant dec31st2016 = getInstantAt(12, 31, 2016)

	static Instant jan1st2017 = getInstantAt(1, 1, 2017)
	static Instant dec31st2017 = getInstantAt(12, 31, 2017)
	
	static Instant now = Instant.now()

	static Instant filterStart = now.toDateTime().minusDays(1).toInstant()
	static Instant filterFifteenDaysFromNow = now.toDateTime().plusDays(15).toInstant()
	static Instant filterThirtyDaysFromNow = now.toDateTime().plusDays(30).toInstant()
	static Instant filterSixtyDaysFromNow = now.toDateTime().plusDays(60).toInstant()
	static Instant filterNinetyDaysFromNow = now.toDateTime().plusDays(90).toInstant()

	def "test daily event spanning a year "() {

		given: "a new daily event"
			def dailyEvent = new DailyRecurringScheduledEvent()
				
		when: "repeated every 1 day for a year"
			dailyEvent.setRepeatInterval(1)
			dailyEvent.setStartRecurrence(start)
			dailyEvent.setEndRecurrence(end)

			def instances = dailyEvent.getScheduledInstancesForTimeframe(start, end, TimeZone.getDefault())
			
		then: "the number of instances should match the count of the number of days in the year"
			instances.size() == count
		
		where: "the start/end dates are as such"
			start		| end			| count
			jan1st2016  | dec31st2016 	| 366 //this is a leap year so will have one extra day!
			jan1st2017  | dec31st2017 	| 365
	}
	
	def "test daily event spanning a year and differing limits on occurences"() {
		
		given: "a new daily event"
			def dailyEvent = new DailyRecurringScheduledEvent()

		when: "repeated every 1 day for a year"
			dailyEvent.setStartRecurrence(jan1st2016)
			dailyEvent.setEndRecurrence(dec31st2016)

			dailyEvent.setRepeatInterval(interval)
			dailyEvent.setOccuranceLimit(limit != null ? new Integer(limit) : null)
			
			def instances = dailyEvent.getScheduledInstancesForTimeframe(jan1st2016, dec31st2016, TimeZone.getDefault())
			
		then: "there are differing instances returned for various values of the interval and occurrence limit"
			instances.size() == count
			
		where: "these intervals and limits determine the instance count"
			interval 	| limit	| count
			1			| null	| 366
			1			| 400	| 366
			1			| 10	| 10
			5			| 10	| 10
			100			| 10	| 4
			121			| 10	| 4
			123			| 10	| 3
			100			| 1		| 1
			366			| null	| 1
			367			| null	| 1
	}

	def "test daily event over several filter values"() {

		given: "a new daily event"
			def dailyEvent = new DailyRecurringScheduledEvent()

		when: "repeated every 1 day for 90 days"
			dailyEvent.setRepeatInterval(1)
			dailyEvent.setStartRecurrence(Instant.now())
			dailyEvent.setEndRecurrence(Instant.now().toDateTime().plusDays(90).toInstant())

			def instances = dailyEvent.getScheduledInstancesForTimeframe(start, end, TimeZone.getDefault())

		then: "the number of the instance should match the count from the table"
			instances.size() == count

		where: "the filter start/end dates are as such"
			start						| end						| count
			filterStart 				| filterFifteenDaysFromNow 	| 15
			filterStart 				| filterThirtyDaysFromNow 	| 30
			filterStart 				| filterSixtyDaysFromNow 	| 60
			filterStart 				| filterNinetyDaysFromNow 	| 90
			filterFifteenDaysFromNow 	| filterThirtyDaysFromNow 	| 15
			filterFifteenDaysFromNow	| filterSixtyDaysFromNow	| 45
	}
	
	def "test 5 day interval event over several filter values"() {
		
		given: "a new daily event"
			def dailyEvent = new DailyRecurringScheduledEvent()

		when: "repeated every 5 day for 90 days"
			dailyEvent.setRepeatInterval(5)
			dailyEvent.setStartRecurrence(Instant.now())
			dailyEvent.setEndRecurrence(Instant.now().toDateTime().plusDays(90).toInstant())

			def instances = dailyEvent.getScheduledInstancesForTimeframe(start, end, TimeZone.getDefault())

		then: "the number of instances should be 3 and there should be 5 days between instances"
			instances.size() == count
			5 == Days.daysBetween(
					instances[0].getExecutionDatetime().toDateTime().withTimeAtStartOfDay(),
					instances[1].getExecutionDatetime().toDateTime().withTimeAtStartOfDay()).getDays()
			5 == Days.daysBetween(
					instances[instances.size-2].getExecutionDatetime().toDateTime().withTimeAtStartOfDay(),
					instances[instances.size-1].getExecutionDatetime().toDateTime().withTimeAtStartOfDay()).getDays()

		where: "the filter start/end dates are as such"
			start						| end						| count
			filterStart 				| filterFifteenDaysFromNow 	| 3
			filterStart 				| filterThirtyDaysFromNow 	| 6
			filterStart 				| filterSixtyDaysFromNow 	| 12
			filterStart 				| filterNinetyDaysFromNow 	| 18
			filterFifteenDaysFromNow 	| filterThirtyDaysFromNow 	| 3
			filterFifteenDaysFromNow	| filterSixtyDaysFromNow	| 9
	}
	
}
