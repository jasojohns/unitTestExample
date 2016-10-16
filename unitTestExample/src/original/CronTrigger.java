/* =============================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2003. All Rights Reserved.
 *
 * ======================== CVS Header - Do Not Modify =========================
 * $Source: /home/cvs/sciquest/components/java/com/sciquest/components/schedulers/triggers/CronTrigger.java,v $
 * $Revision: 1.7 $
 * $Date: 2015/09/13 07:26:39 $
 * Note........:
 *
 * ===================== End of CVS Header - Do Not Modify =====================
 */
package original;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;
import java.util.SortedSet;

import org.apache.commons.collections.map.LRUMap;
import org.joda.time.Instant;

/**
 * Class description here.
 *
 * @author alexey
 */
public final class CronTrigger {

	public static final long ONE_SECOND = 1000;

	public static final long ONE_MINUTE = 60 * ONE_SECOND;

	/** Cache expressions at the thread level to avoid unnecessary locking */
	private static ThreadLocal<Map<String, CronExpression>> expressionCache = new  ThreadLocal<Map<String, CronExpression>>() {

		@SuppressWarnings("unchecked") // Jakarta Collections does not support generics at this time
		@Override
		protected Map<String, CronExpression> initialValue() {
			// TODO Make size configurable
			return new LRUMap(100);
		}
	};

	String definition;
	Instant endTime;
	Instant startTime;
	TriggerState state;

	/**
	 * Constructor
	 */
	public CronTrigger() {
	}

	/**
	 * @return the endTime.
	 */
	public final Instant getEndTime() {
		return endTime;
	}

	/**
	 * @return the startTime.
	 */
	public final Instant getStartTime() {
		return startTime;
	}

	public CronTrigger(CronExpression expression) {
		this();
		this.definition = expression.getPattern();
	}

	// This logic is heavily influenced by the Quartz code
	public Instant computeFireTimeAfter(Instant time) {
		Instant afterTime = time;
		if (getEndTime() != null && getEndTime().isBefore(afterTime)) {
			return null;
		}
		if (getStartTime() != null && getStartTime().isAfter(afterTime)) {
			afterTime = getStartTime().minus(ONE_MINUTE);
		}
		CronExpression cronExpression;
		try {
			cronExpression = getExpression(definition);
		}
		catch (ParseException e) {
			// Normally it should not happen because the expressions are
			// validated on the way in
			throw new RuntimeException(e);
		}

		// Set the starting point
		Calendar calendar = Calendar.getInstance(cronExpression.getTimezone());
		calendar.setLenient(true);
		// move ahead one minute, since we're computing the time *after* the
		// given time
		afterTime = afterTime.plus(ONE_MINUTE);
		// We do not deal with milliseconds or seconds
		calendar.setTimeInMillis(afterTime.getMillis());
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);

		boolean found = false;
		while (!found) {
			// Have we gone past the end time?
			if (getEndTime() != null && getEndTime().getMillis() < calendar.getTimeInMillis()) {
				return null;
			}

			// Minutes
			int minute = calendar.get(Calendar.MINUTE);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			SortedSet<Integer> validMinutes = cronExpression.getValidMinutes();
			SortedSet<Integer> minutesLeft = validMinutes.tailSet(new Integer(minute));
			if (minutesLeft.isEmpty()) {
				minute = validMinutes.first().intValue();
				hour++;
				setCalendarHour(calendar, hour);
			}
			else {
				minute = minutesLeft.first().intValue();
			}
			calendar.set(Calendar.MINUTE, minute);

			// Hours
			int previousHour = -1;
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			SortedSet<Integer> validHours = cronExpression.getValidHours();
			SortedSet<Integer> hoursLeft = validHours.tailSet(new Integer(hour));
			if (hoursLeft.isEmpty()) {
				hour = validHours.first().intValue();
				day++;
			}
			else {
				// Save the previous hour value
				previousHour = hour;
				hour = hoursLeft.first().intValue();
			}
			// Did the hour change?
			if (hour != previousHour) {
				// Set the minutes to the first value
				calendar.set(Calendar.MINUTE, validMinutes.first().intValue());
				// Set the day in case it's changed
				calendar.set(Calendar.DAY_OF_MONTH, day);
			}
			setCalendarHour(calendar, hour);

			// Days
			day = calendar.get(Calendar.DAY_OF_MONTH);
			int previousDay = -1;
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			SortedSet<Integer> validDays = cronExpression.getValidDays(month, year);
			SortedSet<Integer> daysLeft = validDays.tailSet(new Integer(day));
			if (daysLeft.isEmpty()) {
				// Valid days are month-dependent, so if the month changes, we start from scratch
				day = 1;
				month++;
			}
			else {
				previousDay = day;
				day = daysLeft.first().intValue();
			}
			if (day != previousDay) {
				// Reset minutes
				calendar.set(Calendar.MINUTE, 0);
				// Reset hours
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				// Set the day in case it's changed
				calendar.set(Calendar.DAY_OF_MONTH, day);
				// Set the month in case it's changed
				calendar.set(Calendar.MONTH, month);
				continue;
			}

			// Months
			month = calendar.get(Calendar.MONTH);
			year = calendar.get(Calendar.YEAR);
			int previousMonth = -1;
			SortedSet<Integer> validMonths = cronExpression.getValidMonths();
			SortedSet<Integer> monthsLeft = validMonths.tailSet(new Integer(month));
			if (monthsLeft.isEmpty()) {
				month = validMonths.first().intValue();
				year++;
			}
			else {
				previousMonth = month;
				month = monthsLeft.first().intValue();
			}
			if (month != previousMonth) {
				// Reset minutes
				calendar.set(Calendar.MINUTE, 0);
				// Reset hours
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				// Reset day
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				// Set the month in case it's changed
				calendar.set(Calendar.MONTH, month);
				// Set the year
				calendar.set(Calendar.YEAR, year);
				continue;
			}

			// Years
			year = calendar.get(Calendar.YEAR);
			int previousYear = -1;
			SortedSet<Integer> validYears = cronExpression.getValidYears();
			SortedSet<Integer> yearsLeft = validYears.tailSet(new Integer(year));
			if (yearsLeft.isEmpty()) {
				// Out of years
				return null;
			}
			else {
				previousYear = year;
				year = yearsLeft.first().intValue();
			}
			if (year != previousYear) {
				// Reset minutes
				calendar.set(Calendar.MINUTE, 0);
				// Reset hours
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				// Reset day
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				// Reset month
				calendar.set(Calendar.MONTH, 0);
				// Set the year
				calendar.set(Calendar.YEAR, year);
				continue;
			}
			calendar.set(Calendar.YEAR, year);

			// Got here means we found a solution!
			found = true;
		}

		return new Instant(calendar.getTimeInMillis());
	}

	/**
	 * Advance the calendar to the particular hour paying particular attention
	 * to daylight saving problems.
	 *
	 * @param cal
	 * @param hour
	 *
	 * @author Copied from Quartz code
	 */
	private void setCalendarHour(Calendar cal, int hour) {
		cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
		if (cal.get(java.util.Calendar.HOUR_OF_DAY) != hour && hour != 24) {
			cal.set(java.util.Calendar.HOUR_OF_DAY, hour + 1);
		}
	}

	private CronExpression getExpression(String cronExpression)
			throws ParseException {
		Map<String, CronExpression> expressions = expressionCache.get();
		CronExpression compiledExpression = expressions.get(cronExpression);
		if (compiledExpression == null) {
			compiledExpression = new CronExpression(cronExpression);
			expressions.put(cronExpression, compiledExpression);
		}
		return compiledExpression;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public TriggerState getState() {
		return state;
	}

	public void setState(TriggerState state) {
		this.state = state;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}



}