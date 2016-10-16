/* =============================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2002. All Rights Reserved.
 *
 * ======================== CVS Header - Do Not Modify =========================
 * $Source: /home/cvs/sciquest/library/java/com/sciquest/library/general/DateUtils.java,v $
 * $Revision: 1.74 $
 * $Date: 2016/09/27 14:11:47 $
 * Note........:
 *
 * ===================== End of CVS Header - Do Not Modify =====================
 */
package original;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.oro.text.perl.Perl5Util;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;




/**
 * Collection of Date manipulation methods that can replace oft-repeated code.
 * Ideally, functions should be static and final so the compiler will inline them.
 *
 * @author venkat.sadasivam
 */
public class DateUtils {

	// Perl 5 utility object
	private static final Perl5Cache perl5Cache = new Perl5Cache(10);

	private static final String KINDA_ISO_8601_DATE_FORMAT =
	"yyyy-MM-dd'T'HH:mm:ss.SZ";

	//ISO 8601 standard date time format that accepts the time with micro seconds precision
	private static final String KINDA_ISO_8601_DATE_TIME_FORMAT_FOR_PARSING =
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	//ISO 8601 standard date time format that accepts the time with seconds precision
	private static final String ISO_8601_DATE_TIME_FORMAT_SECONDS_PRECISION =
		"yyyy-MM-dd'T'HH:mm:ssZ";

	//ISO 8601 standard date time format that accepts the time with minutes precision
	private static final String ISO_8601_DATE_TIME_FORMAT_MINUTES_PRECISION =
		"yyyy-MM-dd'T'HH:mmZ";

	private static final String KINDA_ISO_8601_DATE_FORMAT_FOR_PARSING =
	"yyyy-MM-dd";

	private static final String ISO_8601_DATE_FORMAT =
	"yyyy-MM-dd";

	public static final String DATE_ONLY_FORMATTER_PATTERN = "yyyyMMdd";
	public static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormat.forPattern(DATE_ONLY_FORMATTER_PATTERN);

	public static final DateTimeFormatter FILE_EXTENSION_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmss");

	public static final DateTimeFormatter ICALENDAR_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'");

	public static final DateTimeFormatter SALESFORCE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public static final String FULL_MONTH_FULL_YEAR_FORMAT = "MMMM yyyy";


	private static final Pattern RFC_3339_PATTERN=Pattern.compile(
			"(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])(?:T([01]\\d|2[0-3]):([0-5]\\d|60)(?::([0-5]\\d|60))?(\\.\\d*)?)?(?:(Z)|([+\\-](?:[01]\\d|2[0-3]):(?:[0-5]\\d|60)))?",
			// YEAR     MONTH             DAY                       HOUR               MINUTE          SEC           FRACSEC        Z   +/-      ZHOUR             ZMIN
			// 1          2                3                        4                   5                6             7            8    9
			Pattern.CASE_INSENSITIVE);

	private static final TimeZone GMT=TimeZone.getTimeZone("GMT");

	/**
	 * Constructor for DateUtils.
	 */
	private DateUtils() {
		throw new Error("DateUtils is just a container for static methods");
	}

	/**
	 * Return a java.util.Date from an RFC 3339 date string.  If the string does not specify a time zone, use the
	 * specified default time zone.
	 *
	 * <pre>
	 *   date-fullyear   = 4DIGIT
	 *   date-month      = 2DIGIT  ; 01-12
	 *   date-mday       = 2DIGIT  ; 01-28, 01-29, 01-30, 01-31 based on
	 *                             ; month/year
	 *   time-hour       = 2DIGIT  ; 00-23
	 *   time-minute     = 2DIGIT  ; 00-59
	 *   time-second     = 2DIGIT  ; 00-58, 00-59, 00-60 based on leap second
	 *                             ; rules
	 *   time-secfrac    = "." 1*DIGIT
	 *   time-numoffset  = ("+" / "-") time-hour ":" time-minute
	 *   time-offset     = "Z" / time-numoffset
	 *
	 *   partial-time    = time-hour ":" time-minute [":" time-second [time-secfrac]]
	 *   full-date       = date-fullyear "-" date-month "-" date-mday
	 *   full-time       = partial-time time-offset
	 *
	 *   date-time       = full-date "T" full-time
	 *
	 *   date-with-zone  = full-date time-offset
	 * </pre>
	 *
	 * @param date in RFC 3339 format
	 * @param defaultTimeZone in case none is specified by the date string. If this parameter is null and no zone is specified in the string, GMT is used.
	 * @return the date parsed, with unspecified time set to the beginning of the day, or null if the string did not represent a date.
	 */
	public static Date parseRFC3339Date(String date, TimeZone defaultTimeZone) {
		Date rval=null;
		if (date==null) {
			return null;
		}
		Matcher m=RFC_3339_PATTERN.matcher(date.trim());
		if (m.matches()) {
			TimeZone zone;
			if (m.group(8)!=null) {
				zone=GMT;
			} else if (m.group(9)!=null) {
				zone=TimeZone.getTimeZone("GMT" + m.group(9));
			} else if (defaultTimeZone!=null) {
				zone=defaultTimeZone;
			} else {
				zone=GMT;
			}
			GregorianCalendar cal=new GregorianCalendar(zone);
			cal.setLenient(false);
			cal.set(Calendar.YEAR, Integer.parseInt(m.group(1)));
			cal.set(Calendar.MONTH, Integer.parseInt(m.group(2))-1);
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(m.group(3)));
			if (m.group(4)!=null) {
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(m.group(4)));
				cal.set(Calendar.MINUTE, Integer.parseInt(m.group(5)));
				cal.set(Calendar.SECOND, m.group(6)!=null?Integer.parseInt(m.group(6)):0);
				cal.set(Calendar.MILLISECOND, m.group(7)!=null?parseMillis(m.group(7)):0);
			} else {
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
			}
			try {
				rval=cal.getTime();
			} catch (IllegalArgumentException e) {
				// some kind of bogus numbers in the date fields, so return null
			}
		}
		return rval;
	}

	private static int parseMillis(String string) {
		StringBuffer buf=new StringBuffer(string);
		if (buf.charAt(0)=='.') {
			buf.deleteCharAt(0);
		}
		while (buf.length()<3) {
			buf.append(0);
		}
		return Integer.parseInt(buf.substring(0, 3));
	}

	/**
	 * This method parse the string value into date with the specified format.
	 * @param dateFormat date format obtained from international context
	 * @param dateValue the string value entered by user.
	 * @return return date object if the datevalue is in correct format, otherwise return null
	 *
	 * @deprecated The standard java library method {@link DateFormat#parse(String)} should be used instead.
	 */
	@Deprecated
	public final static Date parseDate(final DateFormat dateFormat, String dateValue) {
		SimpleDateFormat sdf = (SimpleDateFormat) dateFormat;
		Date date = null;
		try {
			StringBuffer perlPattern = new StringBuffer(sdf.toPattern());

			//Date is always interpreted as number.
			//Date can be minimum 1 digit, maximum 2 digit.
			String format = perlPattern.toString();
			int startIndex = format.indexOf('d');
			int endIndex = format.lastIndexOf('d');
			perlPattern.replace(startIndex, endIndex + 1, "[0-9]{1,2}");

			//If the number of pattern letters is 3 or more,
			//the month is interpreted as text; otherwise, it is interpreted as a number
			format = perlPattern.toString();
			startIndex = format.indexOf('M');
			endIndex = format.lastIndexOf('M');
			int count = (endIndex + 1) - startIndex;
			if (count >= 3) {
				perlPattern.replace(startIndex, endIndex + 1, "[a-zA-Z]{" + count + "}");
			} else {
				perlPattern.replace(startIndex, endIndex + 1, "[0-9]{1,2}");
			}

			//Year is always interpreted as number.
			//Number of year digit should be same as number of pattern letters.
			format = perlPattern.toString();
			startIndex = format.indexOf('y');
			endIndex = format.lastIndexOf('y');
			count = (endIndex + 1) - startIndex;
			perlPattern.replace(startIndex, endIndex + 1, "[0-9]{" + count + "}");

			//This will validate the date value against the generated perl pattern.
			Perl5Util util = perl5Cache.getPerl5();
			boolean isValidDatePattern = util.match("/" + perlPattern.toString() + "$/", dateValue);

			if (isValidDatePattern) {
				sdf.setLenient(false);
				date = sdf.parse(dateValue);
			}
		} catch (ParseException exp) {
			//If exception thrown, then the date is invalid.
		}
		return date;
	}

	/**
	 * Doubles Month's, day's, hour's and minute's etc.,
	 *
	 * Makes it look prettier when the pattern itself is displayed.
	 *
	 * @param pattern
	 */
	public static final String decorateDateFormat(String pattern) {
		StringBuilder sb = new StringBuilder(pattern);
		String[] patternLegs = {
									"MM", // Double Month (M to MM)
									"dd", // Double Day (d to dd)
									"hh", // Double hour (h to hh) 12 hrs pattern
									"HH", // Double hour (H to HH) 24 hrs pattern
									"mm", // Double minute (m to mm)
									"ss" // Double second (s to ss)
								};
		for (String patternLeg : patternLegs) {
			if (sb.indexOf(patternLeg) < 0) {
				int index = sb.indexOf(patternLeg.substring(0, 1));
				if (index >= 0) {
					sb.replace(index, index + 1, patternLeg);
				}
			}
		}
		return sb.toString();
	}


	/**
	 * This method will convert the java date format into Javascript compatible
	 * date format.
	 * <p>
	 * Example m/d/yyyy -> MM/DD/YYYY
	 *
	 * @param javaDateFormat
	 *            java date format taken from international context
	 * @return returns date format compatible for javascript
	 */
	public static final String convertToJavascriptDateFormat(String javaDateFormat) {
		StringBuffer newFormat = new StringBuffer(javaDateFormat.toLowerCase());

		String format = newFormat.toString();
		int startIndex = format.indexOf('d');
		int endIndex = format.lastIndexOf('d');
		int count = (endIndex + 1) - startIndex;
		if (count < 2) {
			newFormat.replace(startIndex, endIndex + 1, "dd");
		}

		format = newFormat.toString();
		startIndex = format.indexOf('m');
		endIndex = format.lastIndexOf('m');
		count = (endIndex + 1) - startIndex;
		if (count <= 2) {
			newFormat.replace(startIndex, endIndex + 1, "mm");
		} else {
			newFormat.replace(startIndex, endIndex + 1, "mon");
		}
		return newFormat.toString();
	}

	/**
	 * Method getStartDateForEndDate. - Returns date for the given enddate
	 * with the start day of the endDate's month
	 * @param endDate
	 * @param format
	 * @return String
	 * @throws ParseException
	 */
	public static final String getStartDateForEndDate(String endDate, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = Calendar.DAY_OF_MONTH;
		Date dte = sdf.parse(endDate);
		cal.setTime(dte);
		cal.set(dayOfMonth, cal.getActualMinimum(dayOfMonth));

		return sdf.format(cal.getTime());
	}

	/**
	 * First tries to convert to a timestamp with the time associated.  If that fails then based on the makeStartDate it will
	 * attempt make the timestamp a startDate or an endDate.  If neither parsing is successfull null is returned.
	 *
	 * Method added to support an extract date which may or may not have a time parameter.
	 *
	 * @param formatWithoutTime the format without time
	 * @param formatWithTime the format with time
	 * @param dateToParse the date to parse
	 * @param makeStartDate the make start date
	 * @return the timestamp
	 */
	public static final Timestamp convertToTimestampPreserveTime(DateFormat formatWithoutTime,DateFormat formatWithTime,String dateToParse,boolean makeStartDate){
	    Timestamp result=DateUtils.convertToTimestamp(dateToParse, formatWithTime);
	    //perform this check because the filter may not have a time.
	    if(result==null){
	    	result=DateUtils.convertToTimestamp(dateToParse, formatWithoutTime,makeStartDate);
	    }
	    return result;
	}
	/**
	 * First tries to convert to a instant with the time associated. If that fails then based on the makeStartDate it will
	 * attempt make the instant a startDate or an endDate.  If neither parsing is successfull null is returned.
	 *
	 * Method added to support an extract date which may or may not have a time parameter.
	 *
	 * @param formatWithoutTime the format without time
	 * @param formatWithTime the format with time
	 * @param dateToParse the date to parse
	 * @param makeStartDate the make start date
	 * @return the instant
	 */
	public static final Instant convertToInstantPreserveTime(DateTimeFormatter formatWithoutTime, DateTimeFormatter formatWithTime, String dateToParse, boolean makeStartDate) {
	  Instant result = DateUtils.convertToInstant(dateToParse, formatWithTime, makeStartDate);
		//perform this check because the filter may not have a time.
		if (result == null) {
			result = DateUtils.convertToInstant(dateToParse, formatWithoutTime, makeStartDate);
		}
		return result;
	}

	/**
	 * Converts the dateString into instant
	 *
	 * @param date
	 * @param formatter
	 * @param startDate
	 * @return Instant
	 */
	public static final Instant convertToInstant(String date, DateTimeFormatter formatter, boolean startDate) {
		return startDate ? startOfDay(formatter.parseLocalDate(date), formatter.getZone()).toInstant() : endOfDay(formatter.parseLocalDate(date), formatter.getZone()).toInstant();
	}
	/**
	 * Convert string date to a Timestamp corresponding to the date and time in the string.
	 * a parseException occurs if no time is present
	 *
	 * @param date the date string
	 * @param format the format obtained from AppUser.getInternationalContext().getDateTimeFormat(DateFormat.SHORT, DateFormat.SHORT);
	 * @return a Timestamp or null if the date string can't be parsed
	 */
	public static final Timestamp convertToTimestamp(String date, DateFormat format) {

		Timestamp timestamp = null;
		java.util.Date _date;
		try {
			_date = format.parse(date);
		} catch (ParseException e) {
			// Invalid date
			return null;
		}
		if (_date != null) {
			Calendar cal = Calendar.getInstance(format.getTimeZone());

			cal.setTime(_date);

			timestamp = new Timestamp(cal.getTime().getTime());
		}
		return timestamp;
	}
	/**
	 * Convert string date to a Timestamp corresponding to the beginning or end of the day.
	 *
	 * @param date the date string
	 * @param format the format obtained from AppUser.getInternationalContext().getDateFormat(DateFormat.SHORT)
	 * @param startDate if true returned timestamp is corresponds to the beginning of the day
	 * @return a Timestamp or null if the date string can't be parsed
	 */
	public static final Timestamp convertToTimestamp(String date, DateFormat format, boolean startDate) {
		return startDate ? startDate(date,format) : endDate(date,format);
	}

	/**
	 * Convert string date to a Timestamp corresponding to the beginning of the day.
	 *
	 * <b>***NOTE: Currently, the returned timestamp is set to midnight of the timezone
	 * provided in the DateFormat parameter.  Previously, it was set to the timezone
	 * of the JVM, which was Eastern Time.  Please adjust the timezone in the
	 * DateFormat parameter as needed before calling, if necessary.***</b>
	 *
	 * @param date the date string
	 * @param format the DateFormat obtained from AppUser.getInternationalContext().getDateFormat(DateFormat.SHORT)
	 * @return a Timestamp or null if the date string can't be parsed
	 */
	public static final Timestamp startDate(String date, DateFormat format) {

		Timestamp timestamp = null;
		java.util.Date _date;
		try {
			_date = format.parse(date);
		} catch (ParseException e) {
			// Invalid date
			return null;
		}
		if (_date != null) {
			Calendar cal = Calendar.getInstance(format.getTimeZone());

			cal.setTime(_date);
			//fix to prevent returning Timestamp with a year greater than 9999 - bad for sql
			if(cal.get(Calendar.YEAR) > 9999) {
				return null;
			}
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			timestamp = new Timestamp(cal.getTime().getTime());
		}
		return timestamp;
	}

	/**
	 * Convert string date to a LocalDate. An IllegalArgumentException occurs if the text to parse is invalid and
	 * an UnsupportedOperationException occurs if parsing is not supported.
	 *
	 * @param date the date string
	 * @param dateFormat the DateTimeFormatter obtained from sessionUser.getInternationalContext().getShortDateFormatterForParsing()
	 * @return a LocalDate OR null if the text to parse is invalid or if parsing is not supported.
	 */
	public static final LocalDate parseLocalDate(String date, DateTimeFormatter dateFormat) {
		try {
			return dateFormat.parseLocalDate(date);
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
			return null;
		}
	}

	/**
	 *
     * Reset to start of day
     *
     * @param inDate
	 */
    public static final Date startDate(Date inDate) {

        Date outDate = null;

        if (inDate != null) {
            Calendar cal = Calendar.getInstance();

            cal.setTime(inDate);
            //fix to prevent returning Timestamp with a year greater than 9999 - bad for sql
			if(cal.get(Calendar.YEAR) > 9999) {
				return null;
			}

            // start date is the beginning of the day
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            outDate = cal.getTime();
        }
        return outDate;
    }

    /**
     * Reset to start of day in the given timezone.
      */
    public static final Date startDate(Date inDate, TimeZone timezone) {
        Date outDate = null;

        if (inDate != null) {
            Calendar cal = Calendar.getInstance(timezone);
            cal.setTime(inDate);

            //fix to prevent returning Timestamp with a year greater than 9999 - bad for sql
			if(cal.get(Calendar.YEAR) > 9999) {
				return null;
			}
            // start date is the beginning of the day
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            outDate = cal.getTime();
        }
        return outDate;
   }

	/**
	 * Convert string date to a Timestamp corresponding to the end of the day.
	 *
	 * <b>***NOTE: Currently, the returned timestamp is set to 23:59:59.999 of the timezone
	 * provided in the DateFormat parameter.  Previously, it was set to the timezone
	 * of the JVM, which was Eastern Time.  Please adjust the timezone in the
	 * DateFormat parameter as needed before calling, if necessary.***</b>
	 *
	 * @param date the date string
	 * @param format the DateFormat obtained from AppUser.getInternationalContext().getDateFormat(DateFormat.SHORT)
	 * @return a Timestamp or null if the date string can't be parsed
	 */
	public static final Timestamp endDate(String date, DateFormat format) {

		Timestamp timestamp = null;
		java.util.Date _date;
		try {
			_date = format.parse(date);
		} catch (ParseException e) {
			// The date is invalid
			return null;
		}

		if (_date != null) {
			Calendar cal = Calendar.getInstance(format.getTimeZone());

			cal.setTime(_date);
			//fix to prevent returning Timestamp with a year greater than 9999 - bad for sql
			if(cal.get(Calendar.YEAR) > 9999) {
				return null;
			}
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);

			timestamp = new Timestamp(cal.getTime().getTime());
		}
		return timestamp;
	}


	/**
	 * Returns a calendar whose date is the same as the calendar parameter's date, but
	 * with the time changed to that specified in the remaining four parameters.
	 *
	 * @param cal - a Calendar instance.  The returned calendar will have the same date, but a new time specified by the rest of the parameters
	 * @param hourOfDay - the hour of the returned calendar in military time - valid values are 0 to 23.  E.g. use 21 for a time during the hour of 9PM
	 * @param minute - the minutes of the returned calendar - valid values are 0 to 59
	 * @param second - the seconds of the returned calendar - valid values are 0 to 59
	 * @param millisecond - the milliseconds of the returned calendar - valid values are 0 to 999
	 *
	 * @return a Calendar with the new time
	 */
	public static final Calendar changeTimeOfDay(Calendar cal,
			int hourOfDay, int minute, int second, int millisecond) {
		if (cal == null) {
			return null;
		}

		Calendar calendar = (Calendar) cal.clone();
		// replace calendar's time
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);

		return calendar;
	}

	/**
	 * Returns a calendar whose date is the same as the parameter's date, but
	 * with the time set to the start of the day
	 *
	 * @param cal
	 *
	 */
	public static final Calendar startOfDay(Calendar cal) {
		return changeTimeOfDay(cal, 0, 0, 0, 0);
	}

	/**
	 * Returns a calendar whose date is the same as the parameter's date, but
	 * with the time set to the end of the day
	 *
	 * @param cal
	 *
	 */
	public static final Calendar endOfDay(Calendar cal) {
		return changeTimeOfDay(cal, 23, 59, 59, 999);
	}

    public static final Calendar startOfMonth(Calendar cal) {
		if (cal == null) {
			return null;
		}

		Calendar calendar = (Calendar) cal.clone();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
        return startOfDay(calendar);
    }

	/**
	 * Determine if two dates are equal.
	 * if both dates are null then they are considered equal.
	 *
	 * @param date1 a Date object
	 * @param date2 a Date object
	 * @return true if dates are equal; false if dates are not equal.
	 */
	public static final boolean safeEquals(Date date1, Date date2) {

		return ObjectUtils.equals(date1, date2);
	}

	public static final boolean safeEquals(Instant instant1, Instant instant2) {
		return safeCompare(instant1, instant2) == 0;
	}

	public static final boolean safeEquals(LocalDate localDate1, LocalDate localDate2) {
		return safeCompare(localDate1, localDate2) == 0;
	}

	/**
	 * Compares 2 dates. Sorts null to the bottom.
	 */
	public static final int safeCompare(Date date1, Date date2) {
		return NullsToBottomComparator.safeCompare(date1, date2);
	}

	public static final int safeCompare(LocalDate date1, LocalDate date2) {
		return NullsToBottomComparator.safeCompare(date1, date2);
	}

	public static final int safeCompare(Instant date1, Instant date2) {
		return NullsToBottomComparator.safeCompare(date1, date2);
	}

	/**
	 * Checks if date1 is inclusively between date2 and date3
	 * @param date1
	 * @param date2
	 * @param date3
	 * @return boolean
	 */
	public static final boolean safeIsBetween(LocalDate date1, LocalDate date2, LocalDate date3) {
		if (date1 != null && date2 != null && date3 != null) {
			return (date1.isAfter(date2) || date1.equals(date2)) && (date1.isBefore(date3) || date1.equals(date3));
		}
		return false;
	}

	/**
	 * Checks if date1 is inclusively between date2 and date3
	 * @param date1
	 * @param date2
	 * @param date3
	 * @return boolean
	 */
	public static final boolean safeIsBetween(DateTime date1, DateTime date2, DateTime date3) {
		if (date1 != null && date2 != null && date3 != null) {
			return (date1.isAfter(date2) || date1.equals(date2)) && (date1.isBefore(date3) || date1.equals(date3));
		}
		return false;
	}

	/**
	 * Checks if date1 is inclusively between date2 and date3
	 * @param date1
	 * @param date2
	 * @param date3
	 * @return boolean
	 */
	public static final boolean safeIsBetween(Date date1, Date date2, Date date3) {
		if (date1 != null && date2 != null && date3 != null) {
			return (date1.after(date2) || date1.equals(date2)) && (date1.before(date3) || date1.equals(date3));
		}
		return false;
	}

	/**
	 * Checks if date 1 is before or equal to date2
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static final boolean safeIsBeforeOrEquals(DateTime date1, DateTime date2) {
		if (date1 != null && date2 != null) {
			return date1.isBefore(date2) || date1.isEqual(date2);
		}
		return false;
	}

	/**
	 * Checks if date 1 is before or equal to date2
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	public static final boolean safeIsBeforeOrEquals(LocalDate date1, LocalDate date2) {
		if (date1 != null && date2 != null) {
			return date1.isBefore(date2) || date1.isEqual(date2);
		}
		return false;
	}
	/**
	 * Generates an ISO-8601 style timestamp string for the given date
	 */
	public static String getISOTimestamp(Date date) {

		if (date == null) {
			return null;
		}
		DateFormat iso8601format =
			new SimpleDateFormat(KINDA_ISO_8601_DATE_FORMAT);
		String formattedDate = iso8601format.format(date);
		// A hack to use RFC822 style timezone and then insert a ':'
		return formattedDate.substring(0, formattedDate.length() - 2)
			+ ":"
			+ formattedDate.substring(
				formattedDate.length() - 2,
				formattedDate.length());
	}

	/**
	 * Generates an ISO-8601 style timestamp string for the current date and time
	 */
	public static final String getISOTimestamp() {
		return getISOTimestamp(new Date(System.currentTimeMillis()));
	}

	/**
	 * Generates an ISO-8601 date string for the given date.
	 * The lexical form is YYYY-MM-DD.
	 *
	 * @see <a href="http://www.w3.org/QA/Tips/iso-date">W3 date</a>
	 * @see <a href="http://www.w3.org/TR/NOTE-datetime">W3 datetime</a>
	 */
	public static String getISODate(Date date) {
		return getISODate(date, null);
	}

	public static String getISODate(Date date, TimeZone tz) {
		if (date == null) {
			return null;
		}
		return getISODateFormat(tz).format(date);
	}

	/**
	 * Returns a ISO-8601 DateFormat.
	 *
	 * @see <a href="http://www.w3.org/QA/Tips/iso-date">W3 date</a>
	 * @see <a href="http://www.w3.org/TR/NOTE-datetime">W3 datetime</a>
	 */
	public static final DateFormat getISODateFormat() {
		return getISODateFormat(null);
	}

	public static final DateFormat getISODateFormat(TimeZone tz) {
		DateFormat df = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
		if (tz != null) {
			df.setTimeZone(tz);
		}
		return df;
	}

	public static final Date parseISOTimestamp(String dateTimeString, boolean lenient) {
		if (dateTimeString == null) {
			return null;
		}
		Date parsedDate = parseISOTimestamp(dateTimeString, KINDA_ISO_8601_DATE_TIME_FORMAT_FOR_PARSING, lenient);
		//TD 7556: Trying to parse the dateTime string given using three different valid formats
		//to parse if the string is given in any of the formats.
		if (parsedDate == null) {
			parsedDate = parseISOTimestamp(dateTimeString, ISO_8601_DATE_TIME_FORMAT_SECONDS_PRECISION, lenient);
			if (parsedDate == null) {
				parsedDate = parseISOTimestamp(dateTimeString, ISO_8601_DATE_TIME_FORMAT_MINUTES_PRECISION, lenient);
				if (parsedDate == null) {
					parsedDate = parseISOTimestamp(dateTimeString, ISO_8601_DATE_FORMAT, lenient);
				}
			}
		}
		return parsedDate;
	}

	/**
	 * Parse the date in yyyy-mm-dd format, ignore everything else that comes after it assuming it is separated by a space or a T
	 * @return null if the date is invalid
	 */
	public static final LocalDate parseISODateIgnoreTimePortionAndInvalidFormat(String date) {
		if (date == null) {
			return null;
		}
		String dateString = date.trim();
		if (dateString.isEmpty()) {
			return null;
		}
		int separatorIndex = dateString.indexOf('T');
		if (separatorIndex < 0) {
			separatorIndex = dateString.indexOf(' ');
		}
		if (separatorIndex > 0) {
			dateString = dateString.substring(0, separatorIndex);
		}
		try {
			LocalDate localDate = LocalDate.parse(dateString);
			// Make sure the year is not higher than 9999 - this is the database limit
			if (localDate.getYear() > 9999) {
				return null;
			}
			return localDate;
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Parses the given string against the "KINDA_ISO_8601_DATE_TIME_FORMAT_FOR_PARSING" date
	 * format to return a Date object.
	 *
	 * @see #parseRFC3339Date
	 * @param dateTimeString
	 */
	public static Date parseISOTimestamp(String dateTimeString) {
		return parseISOTimestamp(dateTimeString, true);
	}

	/**
	 * Parses the date Time provided along with the locale.
	 *
	 * @param dateTimeString
	 * @param iso8601DateTimeFormat
	 */
	public static Date parseISOTimestamp(String dateTimeString, String iso8601DateTimeFormat, boolean lenient) {
		try {
			DateFormat iso8601format = new SimpleDateFormat(iso8601DateTimeFormat);
			iso8601format.setLenient(lenient);

			StringBuffer dateBuffer = new StringBuffer(dateTimeString);

			// delete the : in the middle of the timezone designation
			if (dateBuffer.charAt(dateBuffer.length() - 3) == ':') {
				dateBuffer.deleteCharAt(dateBuffer.length() - 3);
			}
			return iso8601format.parse(dateBuffer.toString());
		} catch (ParseException pe) {
			return null;
		}
	}

	public static Date parseISODate(String dateString, TimeZone timeZone, boolean lenient) {
		if (dateString == null) {
			return null;
		}
		// Get the DateFormat for ISO 8601 date format
		DateFormat iso8601format = new SimpleDateFormat(KINDA_ISO_8601_DATE_FORMAT_FOR_PARSING);
		iso8601format.setLenient(lenient);
		iso8601format.setTimeZone(timeZone);

		try {
			return iso8601format.parse(dateString);
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * Parses and validates the given date string for ISO8601 format
	 *
	 * @param dateString
	 * @param timeZone
	 */
	public static Date parseISODate(String dateString, TimeZone timeZone) {
		return parseISODate(dateString, timeZone, true);
	}

	/**
	 * This method calculates the difference between two dates.  The difference can be calculated by hour, day, minute or second
	 * determined by <code>calendarItem</code>. The result is rounded down.
	 *
	 * @param calendarItem Calendar.DAY_OF_MONTH, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND
	 * @param d1	from date
	 * @param d2	to date
	 * @throws IllegalArgumentException if the calendar item is not one of the valid values
	 */
	public static long calcDateDifference(int calendarItem, Date d1, Date d2) {
		int divider;
		switch (calendarItem) {
			case Calendar.DAY_OF_MONTH:
				divider = 24 * 60 * 60 * 1000;
				break;
			case Calendar.HOUR:
				divider = 60 * 60 * 1000;
				break;
			case Calendar.MINUTE:
				divider = 60 * 1000;
				break;
			case Calendar.SECOND:
				divider = 1000;
				break;

			default:
				throw new IllegalArgumentException("Unsupported calendar item code " + calendarItem);
		}
		return (d2.getTime() - d1.getTime()) / divider;
	}

	public static DateTime startOfDay(LocalDate date, DateTimeZone timezone) {
		return date.toDateTimeAtStartOfDay(timezone);
	}

	public static DateTime endOfDay(LocalDate date, DateTimeZone timezone) {
		return date.toDateTimeAtStartOfDay(timezone).withTime(23, 59, 59, 999);
	}

	/** Converts a LocalDate to yyyymmdd format for storing in strings like audit trail or attributes */
	public static final String toInternalStorageStringValue(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_ONLY_FORMATTER.print(date);
	}

	/** Converts yyyymmdd string back to LocalDate
	 * @throws IllegalArgumentException if the string is not in a correct format
	 */
	public static final LocalDate fromInternalStorageString(String value) {
		if (value == null) {
			return null;
		}
		return DATE_ONLY_FORMATTER.parseLocalDate(value);
	}

	/** Returns Sunday as the first day of the week. Joda-time follows ISO standard Monday as the first day of the week */
	public static final LocalDate getFirstDayOfWeekSunday(LocalDate date) {
		if (date.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			return date;
		}
		return date.minusWeeks(1).withDayOfWeek(DateTimeConstants.SUNDAY);
	}

	/** Returns Saturday as the last day of the week. Joda-time follows ISO standard Sunday as the last day of the week */
	public static final LocalDate getLastDayOfWeekSaturday(LocalDate date) {
		if (date.getDayOfWeek() == DateTimeConstants.SATURDAY) {
			return date;
		}
		if (date.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			return date.plusWeeks(1).withDayOfWeek(DateTimeConstants.SATURDAY);
		}
		return date.withDayOfWeek(DateTimeConstants.SATURDAY);
	}

	public static final boolean safeIsBefore(Instant date1, Instant date2) {
		boolean isBefore = true;
		if(null == date1) { // skip
		} else if(null == date2) { // skip
		} else {
			isBefore = date1.isBefore(date2);
		}
		return isBefore;
	}

	/**
	 * Returns true if the two dates and times as LocalDateTimes are equal.
	 *
	 * @param date1
	 * @param date1TimeZone
	 * @param date2
	 * @param date2TimeZone
	 * @return boolean
	 */
	public static final boolean localDateTimeEquals(Instant date1, TimeZone date1TimeZone, Instant date2, TimeZone date2TimeZone) {
		LocalDateTime dateTime1 = null;
		if (date1 != null && date1TimeZone != null) {
			dateTime1 = date1.toDateTime().withZone(DateTimeZone.forTimeZone(date1TimeZone)).toLocalDateTime();
		}
		LocalDateTime dateTime2 = null;
		if (date2 != null && date2TimeZone != null) {
			dateTime2 = date2.toDateTime().withZone(DateTimeZone.forTimeZone(date2TimeZone)).toLocalDateTime();
		}
		return Objects.equals(dateTime1, dateTime2);
	}

	public static final boolean safeIsAfterNow(Instant date) {
		return safeIsAfter(date, Instant.now());
	}

	/**
	 * Performs a null safe date1.isAfter(date2) check
	 */
	public static final boolean safeIsAfter(Instant date1, Instant date2) {
		boolean isAfter = true;
		if(null == date1) { // skip
		} else if(null == date2) { // skip
		} else {
			isAfter = date1.isAfter(date2);
		}
		return isAfter;
	}

	/**
	 * Performs a null safe date1.isAfter(date2) check
	 */
	public static final boolean safeIsAfter(LocalDate date1, LocalDate date2) {
		boolean isAfter = true;
		if (date1 == null) { // skip
		} else if (date2 == null) { // skip
		} else {
			isAfter = date1.isAfter(date2);
		}
		return isAfter;
	}

	/**
	 * Returns true if date 1 is before or equal to date2.
	 *
	 */
	public static boolean safeIsBeforeOrEqual(Instant date1, Instant date2) {
		if (date1 != null && date2 != null) {
			return date1.isBefore(date2) || date1.isEqual(date2);
		}
		return false;
	}

	/**
	 * Returns true if date 1 is after or equal to date2.
	 *
	 */
	public static boolean safeIsAfterOrEqual(Instant date1, Instant date2) {
		if (date1 != null && date2 != null) {
			return date1.isAfter(date2) || date1.isEqual(date2);
		}
		return false;
	}

	/**
	 * Returns the minimum of two instances. If one is null and the other is not null, the not null date
	 * is returned. If both are null then a null is returned.
	 * @param date1
	 * @param date2
	 * @return Instant
	 */
	public static Instant safeMin(Instant date1, Instant date2) {
		if (date1 != null && date2 == null) {
			return date1;
		}

		if (date1 == null && date2 != null) {
			return date2;
		}

		if (date1 != null && date2 != null) {
			if (date1.isAfter(date2)) {
				return date2;
			} else {
				return date1;
			}
		}

		return null;
	}

	/**
	 * Returns the maximumn of two instances. If one is null and the other is not null, the not null date
	 * is returned. If both are null then a null is returned.
	 * @param date1
	 * @param date2
	 * @return Instant
	 */
	public static Instant safeMax(Instant date1, Instant date2) {
		if (date1 != null && date2 == null) {
			return date1;
		}

		if (date1 == null && date2 != null) {
			return date2;
		}

		if (date1 != null && date2 != null) {
			if (date1.isAfter(date2)) {
				return date1;
			} else {
				return date2;
			}
		}

		return null;
	}

}