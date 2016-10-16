package example

import org.joda.time.Instant

import spock.lang.*

class SpockDailyEventTest extends SpockEventTestCase {

	def jan1st2016 = getInstantAt(1, 1, 2016)
	def dec31st2016 = getInstantAt(12, 31, 2016)

	static now = Instant.now()

	static filterStart = now.toDateTime().minusDays(1).toInstant()
	static filterFifteenDaysFromNow = now.toDateTime().plusDays(15).toInstant()
	static filterThirtyDaysFromNow = now.toDateTime().plusDays(30).toInstant()
	static filterSixtyDaysFromNow = now.toDateTime().plusDays(60).toInstant()
	static filterNinetyDaysFromNow = now.toDateTime().plusDays(90).toInstant()

	def "test daily event over 1 year"() {

		given: "a new daily event"
			def dailyEvent = new DailyRecurringScheduledEvent()

		when: "repeated every 1 day for a year"
			dailyEvent.setRepeatInterval(1)
			dailyEvent.setStartRecurrence(jan1st2016)
			dailyEvent.setEndRecurrence(dec31st2016)

		then: "there are 366 instances returned for the event over the course of one full year"
			dailyEvent.getScheduledInstancesForTimeframe(jan1st2016, dec31st2016, TimeZone.getDefault()).size() == 366
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

}
