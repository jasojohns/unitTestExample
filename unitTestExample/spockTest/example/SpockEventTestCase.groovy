package example

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.Instant

import spock.lang.*

class SpockEventTestCase extends spock.lang.Specification {

	public static Instant getInstantAt(int month, int day, int year) {
		return getInstantAt(month, day, year, DateTimeZone.forTimeZone(TimeZone.getDefault()))
	}

	public static Instant getInstantAt(int month, int day, int year, DateTimeZone zone) {
		DateTime dateTime = new DateTime(year, month, day, 12, 0, zone)
		return dateTime.toInstant()
	}
}
