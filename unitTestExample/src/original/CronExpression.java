/* =============================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2003. All Rights Reserved.
 *
 * ======================== CVS Header - Do Not Modify =========================
 * $Source: /home/cvs/sciquest/components/java/com/sciquest/components/schedulers/triggers/CronExpression.java,v $
 * $Revision: 1.5 $
 * $Date: 2015/09/13 07:26:39 $
 * Note........:
 *
 * ===================== End of CVS Header - Do Not Modify =====================
 */
package original;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

import org.apache.oro.text.perl.Perl5Util;


/**
 * Constructs and stores the internal representation of the cron expression
 *
 * @author alexey
 */
public final class CronExpression {

	private static final int[] LAST_DAYS_OF_MONTH =
		{
			31, // January
			28, // February (need special handling for leap years)
			31, // March
			30, // April
			31, // May
			30, // June
			31, // July
			31, // August
			30, // September
			31, // October
			30, // November
			31  // December
		};

	private final String expression;

	private TimeZone timezone;

	private final SortedSet<Integer> validMinutes = new TreeSet<Integer>();

	private final SortedSet<Integer> validHours = new TreeSet<Integer>();

	private final SortedSet<Integer> validMonths = new TreeSet<Integer>();

	private final SortedSet<Integer> validYears = new TreeSet<Integer>();

	private final boolean[] validDaysOfMonth = new boolean[32];

	private boolean useWeekdays;

	private final boolean[][] weekdayMatrix = new boolean[6][7];

	public static final int FIELD_MINUTES = 0;
	public static final int FIELD_HOURS = 1;
	public static final int FIELD_DAYS = 2;
	public static final int FIELD_MONTHS = 3;
	public static final int FIELD_WEEKDAYS = 4;
	public static final int FIELD_YEARS = 5;
	public static final int FIELD_TIMEZONE = 6;

	private static final int[] MIN_FIELD_VALUES = {0, 0, 0, 1, 0, 2000};
	private static final int[] MAX_FIELD_VALUES = {59, 23, 31, 12, 6, 2100};	// TODO hardcoded year!

	// Keep a copy of perl5 by thread
	private static final Perl5Cache perl5Cache = new Perl5Cache(100);		// TODO hardcoded capacity

	private static final String PATTERN_ALL = "*";

	private static final String PATTERN_SEPERATOR = " ";
	/**
	 * Parses the cron expression into an internal representation
	 */
	public CronExpression(String cronExpression) throws ParseException {
		super();
		expression = cronExpression;
		parseExpression(cronExpression);
	}

	/**
	 * Form the pattern and parses the cron expression into an internal representation
	 */
	public CronExpression(Integer minute, Integer hour, Integer dayOfMonth, Integer month, Integer dayOfWeek, Integer year, TimeZone timeZone) throws ParseException {
		super();
		expression = getPattern(minute, hour, dayOfMonth, month, dayOfWeek, year, timeZone);
		parseExpression(expression);
	}

	/**
	 * Parses the cron expression into an internal representation
	 */
	protected void parseExpression(String cronExpression) throws ParseException {
		String[] fields = cronExpression.split("\\s+");
		if (fields.length != 7) {
			throw new ParseException("Expecting 7 fields", 0);
		}
		// Get all fields
		String dayExpr = fields[FIELD_DAYS];
		String weekdayExpr = fields[FIELD_WEEKDAYS];
		String timezoneExpr = fields[FIELD_TIMEZONE];

		if (!weekdayExpr.equals("*") && !dayExpr.equals("*")) {
			throw new ParseException("Specifying both weekdays and month days is not supported", 0);
		}

		useWeekdays = !weekdayExpr.equals("*");
		timezone = TimeZone.getTimeZone(timezoneExpr);

		for (int i = 0; i <= 5; i++) {
			parseField(fields[i], i);
		}
	}

	protected void parseField(String field, int fieldType) throws ParseException {
		// TODO Add better syntax checking
		// TODO Add the ability to specify symbolic months and weekdays

		 Perl5Util perl5 = perl5Cache.getPerl5();

		// First split the comma-separated list
		String[] elements = field.split(",");
		for (int i = 0; i < elements.length; i++) {
			String element = elements[i];
			int minValue = -1;
			int maxValue = -1;
			int stepValue = 1;
			int weekNumber = -1;
			int literalValue = -1;
			boolean isRange = false;

			try {
				if (element.equals("*")) {
					minValue = getMinValue(fieldType);
					maxValue = getMaxValue(fieldType);
					isRange = true;
				}
				else if (element.equals("L")) {
					if (fieldType != FIELD_DAYS) {
						throw new ParseException("L is allowed for day of month only", 0);
					}
					literalValue = 0;
				}
				else if (perl5.match("%^\\d+$%", element)) {
					// Single value
					literalValue = Integer.parseInt(element);
				}
				else if (perl5.match("%^\\*/(\\d+)$%", element)) {
					// Full range with *
					minValue = getMinValue(fieldType);
					maxValue = getMaxValue(fieldType);
					stepValue = Integer.parseInt(perl5.getMatch().group(1));
					isRange = true;
				}
				else if (perl5.match("%^(\\d+)-(\\d+)/(\\d+)$%", element)) {
					// Full range
					minValue = Integer.parseInt(perl5.getMatch().group(1));
					maxValue = Integer.parseInt(perl5.getMatch().group(2));
					stepValue = Integer.parseInt(perl5.getMatch().group(3));
					isRange = true;
				}
				else if (perl5.match("%^(\\d+)-(\\d+)$%", element)) {
					// Simple range
					minValue = Integer.parseInt(perl5.getMatch().group(1));
					maxValue = Integer.parseInt(perl5.getMatch().group(2));
					isRange = true;
				}
				else if (perl5.match("%^(\\d+)#(\\d+|L)$%", element)) {
					// Weekday number
					if (fieldType != FIELD_WEEKDAYS) {
						throw new ParseException("Week numbers are only allowed in the weekday field: " + element, 0);
					}
					String literalValueString = perl5.getMatch().group(1);
					literalValue = Integer.parseInt(literalValueString);
					String weekNumberString = perl5.getMatch().group(2);
					if (weekNumberString.equals("L")) {
						weekNumber = 0;
					}
					else {
						weekNumber = Integer.parseInt(weekNumberString);
						if (weekNumber < 1 || weekNumber > 5) {
							throw new ParseException("Value is outside allowed range: " + element, 0);
						}
					}
				}
				else {
					throw new ParseException("Invalid expression: " + element, 0);
				}
			}
			catch (NumberFormatException e) {
				throw new ParseException("Invalid expression: " + element, 0);
			}

			// Check the values
			if (isRange) {
				if (minValue < getMinValue(fieldType) || maxValue > getMaxValue(fieldType)) {
					throw new ParseException("Value is outside allowed range: " + element, 0);
				}
				if (minValue > maxValue) {
					throw new ParseException("The range is backwards: " + element, 0);
				}
				if (stepValue < 1) {
					throw new ParseException("Step value is outside allowed range: " + element, 0);
				}
			}
			else {
				if (literalValue < getMinValue(fieldType) || literalValue > getMaxValue(fieldType)) {
					throw new ParseException("Value is outside allowed range: " + element, 0);
				}
			}

			// Parsing is done. Now populate internal structures
			switch (fieldType) {
				case FIELD_MINUTES:
					if (isRange) {
						populateSet(validMinutes, minValue, maxValue, stepValue);
					}
					else {
						validMinutes.add(new Integer(literalValue));
					}
					break;

				case FIELD_HOURS:
					if (isRange) {
						populateSet(validHours, minValue, maxValue, stepValue);
					}
					else {
						validHours.add(new Integer(literalValue));
					}
					break;

				case FIELD_DAYS:
					if (isRange) {
						for (int v = minValue; v <= maxValue; v += stepValue) {
							validDaysOfMonth[v] = true;
						}
					}
					else {
						validDaysOfMonth[literalValue] = true;
					}
					break;

				case FIELD_MONTHS:
					if (isRange) {
						// Store months with base 0
						populateSet(validMonths, minValue - 1, maxValue - 1, stepValue);
					}
					else {
						validMonths.add(new Integer(literalValue - 1));
					}
					break;

				case FIELD_WEEKDAYS:
					if (isRange) {
						for (int v = minValue; v <= maxValue; v += stepValue) {
							// Any week
							for (int w = 0; w <= 5; w++) {
								weekdayMatrix[w][v] = true;
							}
						}
					}
					else {
						if (weekNumber >= 0) {
							// Specific week only
							weekdayMatrix[weekNumber][literalValue] = true;
						}
						else {
							// Any week
							for (int w = 0; w <= 5; w++) {
								weekdayMatrix[w][literalValue] = true;
							}
						}
					}
					break;

				case FIELD_YEARS:
					// TODO Reduce garbage creation
					if (isRange) {
						for (int v = minValue; v <= maxValue; v += stepValue) {
							validYears.add(new Integer(v));
						}
					}
					else {
						validYears.add(new Integer(literalValue));
					}
					break;

				default:
					throw new RuntimeException("Should never get here");
			}
		}
	}

	public static int getMinValue(int fieldType) {
		return MIN_FIELD_VALUES[fieldType];
	}

	public static int getMaxValue(int fieldType) {
		return MAX_FIELD_VALUES[fieldType];
	}

	private void populateSet(Set<Integer> set, int minValue, int maxValue, int stepValue) {
		for (int v = minValue; v <= maxValue; v += stepValue) {
			set.add(new Integer(v));
		}
	}

	public final SortedSet<Integer> getValidMinutes() {
		return validMinutes;
	}

	public final SortedSet<Integer> getValidHours() {
		return validHours;
	}

	public final SortedSet<Integer> getValidMonths() {
		return validMonths;
	}

	public final SortedSet<Integer> getValidYears() {
		return validYears;
	}

	public SortedSet<Integer> getValidDays(int month, int year) {
		SortedSet<Integer> resultSet = new TreeSet<Integer>();
		if (useWeekdays) {
			// Go through the matrix and get a date for every valid weekday/week number combination
			for (int weekNumber = 0; weekNumber < 6; weekNumber++) {
				for (int weekday = 0; weekday < 7; weekday++) {
					if (weekdayMatrix[weekNumber][weekday]) {
						int realWeekNumber = (weekNumber == 0 ? -1 : weekNumber);
						int dayOfMonth = getWeekdayDate(month, year, weekday+1, realWeekNumber);
						if (dayOfMonth > 0) {
							resultSet.add(new Integer(dayOfMonth));
						}
					}
				}
			}
		}
		else {
			// Determine the last day of the month
			int lastDay = getLastDayOfMonth(month, year);
			// Add all valid days that are less or equals the last day
			for (int i = 1; i <= lastDay; i++) {
				if (validDaysOfMonth[i]) {
					resultSet.add(new Integer(i));
				}
			}
			// Add the last day if requested
			if (validDaysOfMonth[0]) {
				resultSet.add(new Integer(lastDay));
			}
		}
		return resultSet;
	}

	/**
	 * @return the timezone.
	 */
	public final TimeZone getTimezone() {
		return timezone;
	}

	public final String getPattern() {
		return expression;
	}

	/**
	 * Method to form pattern string
	 */
	private String getPattern(Integer minute, Integer hour, Integer dayOfMonth, Integer month, Integer dayOfWeek, Integer year, TimeZone timeZone) {
		StringBuilder pattern = new StringBuilder();
		Integer[] values = {minute, hour, dayOfMonth, month, dayOfWeek, year};
		for(int timeIndex = 0; timeIndex < values.length; timeIndex++) {
			if(values[timeIndex] != null) {
				pattern.append(values[timeIndex].intValue());
			}else {
				pattern.append(PATTERN_ALL);
			}
			pattern.append(PATTERN_SEPERATOR);
		}
		pattern.append(timeZone.getID());
		return pattern.toString();
	}

	public static int getWeekdayDate(int month, int year, int dayOfWeek, int weekNumber) {
    	// PERFORMANCE Consider caching the Calendar instance somewhere
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, weekNumber);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int newMonth = calendar.get(Calendar.MONTH);
		if (newMonth == month) {
			return dayOfMonth;
		}
		else {
			return -1;
		}
    }

	public static int getLastDayOfMonth(int month, int year) {
    	if (month == 1) {
    		// Special treatment for February
    		if (isLeapYear(year)) {
    			return 29;
    		}
    		else {
    			return 28;
    		}
    	}
    	else {
    		return LAST_DAYS_OF_MONTH[month];
    	}
    }

	/**
	 * Determines whether the particular year is a leap year.
	 *
	 * Taken from Quartz code.
	 *
	 * @param year		4-digit year
	 */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}