package original;

import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationFieldType;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import example.RecurringScheduleDayOfWeekEnum;
import example.RecurringScheduleFrequencyEnum;
import example.RecurringScheduleWeekOfMonthEnum;

public class RecurringScheduleUtils {
	public static int MAX_NEXTRUN = 60;
	public static final String JSON_TIMEZONE = "Timezone";
	public static final String JSON_STARTTIME = "StartTime";
	public static final String JSON_ENDTIME = "EndTime";
	public static final String JSON_STATUS = "Status";
	public static final String JSON_FREQUENCY = "Frequency";
	public static final String JSON_MONTHLYWEEK = "MonthlyWeek";
	public static final String JSON_EVERY_X_MONTHS = "EveryXMonths";
	public static final String JSON_MONTHLYDAYOFWEEK = "MonthlyDayOfWeek";
	public static final String JSON_WEEKLYDAYSOFWEEK = "WeeklyDaysOfWeek";
	public static final String JSON_OCCURENCELIMIT = "OccurenceLimit";
	private static final String CRON_PATTERN_SEPERATOR = " ";
	private static final String CRON_PATTERN_ALL = "*";
	private static final String CRON_PATTERN_LAST = "L";


	// TODO - These fields are new and need to be introduced into the utility methods in this class
	public static final String JSON_YEARLYMONTH = "YearlyMonth";
	public static final String JSON_MONTHLYDAY = "MonthlyDay";
	public static final String JSON_REPEATINTERVAL = "RepeatInterval";

	public static JSONObject buildDetailsJSONObject(TimeZone timezone, Instant startTime, Instant endTime, RecurringScheduleStatusEnum scheduleStatus, RecurringScheduleFrequencyEnum scheduleFrequency, Integer months) {
		try {
			JSONObject json = new JSONObject();

			if (timezone != null) {
				json.put(JSON_TIMEZONE, timezone.getID());
			}

			if (startTime != null) {
				json.put(JSON_STARTTIME, startTime.toString());
			}

			if (endTime != null) {
				json.put(JSON_ENDTIME, endTime.toString());
			}

			if (scheduleStatus != null) {
				json.put(JSON_STATUS, scheduleStatus.name());
			}

			if (months != null && months.intValue() != 0) {
				json.put(JSON_EVERY_X_MONTHS, months.intValue());
			}

			json.put(JSON_FREQUENCY, scheduleFrequency.name());

			return json;
		} catch (JSONException e) {
			throw new RuntimeException("Unable to create new JSONObject");
		}

	}

	public static JSONObject buildDetailsJSONObject(TimeZone timezone, Instant startTime, Instant endTime, RecurringScheduleStatusEnum scheduleStatus, RecurringScheduleFrequencyEnum scheduleFrequency) {
		return buildDetailsJSONObject(timezone, startTime, endTime, scheduleStatus, scheduleFrequency, null);
	}


	public static String getValueFromJson(JSONObject jSONObject, String value) {
		try {
			return jSONObject.getString(value);
		} catch (JSONException e) {
			// nothing
		}
		return null;
	}

	public static RecurringScheduleFrequencyEnum getScheduleFrequency(JSONObject json) {
		return RecurringScheduleFrequencyEnum.valueOf(getValueFromJson(json,RecurringScheduleUtils.JSON_FREQUENCY));
	}

	public static String getXMonths(JSONObject json) {
		return getValueFromJson(json,RecurringScheduleUtils.JSON_EVERY_X_MONTHS);
	}

	public static RecurringScheduleDayOfWeekEnum getMonthlyDayOfWeek(JSONObject json) {
		String internalName = getValueFromJson(json,RecurringScheduleUtils.JSON_MONTHLYDAYOFWEEK);
		if(internalName != null) {
			return RecurringScheduleDayOfWeekEnum.valueOf(internalName);
		} else {
			return null;
		}
	}

	public static RecurringScheduleWeekOfMonthEnum getMonthlyWeekOfMonth(JSONObject json) {
		String internalName = getValueFromJson(json,RecurringScheduleUtils.JSON_MONTHLYWEEK);
		if(internalName != null) {
			return RecurringScheduleWeekOfMonthEnum.valueOf(internalName);
		} else {
			return null;
		}
	}

	public static Instant getStartTime(JSONObject json) {
		return getInstantValueFromJson(json, RecurringScheduleUtils.JSON_STARTTIME);
	}

	public static Instant getEndTime(JSONObject json) {
		return getInstantValueFromJson(json, RecurringScheduleUtils.JSON_ENDTIME);
	}

	public static Instant getInstantValueFromJson(JSONObject jSONObject, String value) {
		String stringValue = getValueFromJson(jSONObject, value);
		if (StringUtils.isNotEmpty(stringValue)) {
			return Instant.parse(stringValue);
		}
		return null;
	}

	public static Instant calculateNextExec(JSONObject json) {
		TimeZone zone = getTimezone(json);
		Instant startTime = getStartTime(json);
		Instant endTime = getEndTime(json);
		RecurringScheduleDayOfWeekEnum monthlyDayOfWeekSelection = null;
		RecurringScheduleWeekOfMonthEnum monthlyWeekOfMonthSelection = null;
		Collection<RecurringScheduleDayOfWeekEnum> weeklyDays = null;
		RecurringScheduleFrequencyEnum frequency = null;
		String internalName = getValueFromJson(json, JSON_FREQUENCY);

		if (StringUtils.isNotEmpty(internalName)) {
			frequency = RecurringScheduleFrequencyEnum.valueOf(internalName);
		}

		// Based on the frequency we populate the enum (monthly or weekly)
		if (RecurringScheduleFrequencyEnum.MONTHLY.equals(frequency)){
			monthlyDayOfWeekSelection = getMonthlyDayOfWeek(json);
			monthlyWeekOfMonthSelection = getMonthlyWeekOfMonth(json);
		} else {
			weeklyDays = getWeeklyDaysOfWeek(json);
		}

		return calculateNextExec(zone,  startTime, null, endTime, monthlyWeekOfMonthSelection,
				 monthlyDayOfWeekSelection,	weeklyDays, getXMonths(json), frequency);
	}

	public static Instant calculateNextExec(TimeZone zone, Instant startTime, Instant lastRun, Instant endTime,
			RecurringScheduleWeekOfMonthEnum monthlyWeekOfMonthSelection,
			RecurringScheduleDayOfWeekEnum monthlyDayOfWeekSelection,
			Collection<RecurringScheduleDayOfWeekEnum> weeklyDays, String xmonths, RecurringScheduleFrequencyEnum frequency) {
		Instant exec;
		if (monthlyWeekOfMonthSelection != null) {
			exec = calculateMonthlyWeekDay( zone,  startTime,  lastRun,  endTime,
					 monthlyWeekOfMonthSelection,
					 monthlyDayOfWeekSelection,	 xmonths);
			return exec;
		} else {
			// only for new schedules and ones coming out of pending
			if (startTime.isBefore(Instant.now()) && frequency.equals(RecurringScheduleFrequencyEnum.MONTHLY) && lastRun == null) {
				Instant nextRun = startTime;
				Instant now = new Instant(Instant.now());

				while ((nextRun.isBefore(now))) {
					// add a minute to nextRun to trigger a new date
					Instant i = nextRun.toDateTime().withFieldAdded(DurationFieldType.minutes(), 1).toInstant();

					nextRun = i;
					Instant newRun = calculateNextExecTime(zone, startTime, i, endTime, monthlyWeekOfMonthSelection,
						 monthlyDayOfWeekSelection,	weeklyDays, xmonths, frequency);

					// jump to newRun date
					String newRunString = newRun!=null?newRun.toString():"newRun empty";
					System.out.println("calculateNextExec Monthly by start date: Start Time is " + startTime.toString() + " Next Run time: " + newRunString + " Now: " + Instant.now());
					nextRun = newRun;
				}
				exec = nextRun;
				// use current time if start date is in the future for monthly by day ONLY
			} else if ((startTime.isAfterNow() || startTime.isEqualNow()) && frequency.equals(RecurringScheduleFrequencyEnum.MONTHLY)) {
				exec = startTime;

			} else { // for weekly
				exec = calculateNextExecTime(zone, startTime, lastRun, endTime,	 monthlyWeekOfMonthSelection, monthlyDayOfWeekSelection,
						weeklyDays, xmonths, frequency);
			}
		}
		return exec;
	}

	public static Instant calculateNextExecTime(TimeZone zone, Instant startTime, Instant lastRun, Instant endTime,
			RecurringScheduleWeekOfMonthEnum monthlyWeekOfMonthSelection,
			RecurringScheduleDayOfWeekEnum monthlyDayOfWeekSelection,
			Collection<RecurringScheduleDayOfWeekEnum> weeklyDays, String xmonths, RecurringScheduleFrequencyEnum frequency) {  //weekly day is first in selection if there are multiple selected

		CronTrigger schedTrigger;
		CronExpression cronExpression = null;

		try {
			cronExpression = createCronExpression(startTime, zone, monthlyWeekOfMonthSelection,monthlyDayOfWeekSelection,	weeklyDays);
		} catch (ParseException pse) {
			throw new IllegalArgumentException("Invalid date parameters.");
		}
		schedTrigger = new CronTrigger(cronExpression);
		schedTrigger.setStartTime(startTime);
		// We are purposely not setting end time // DEVNOTE mphilip|14.3|Jan 5, 2015  revist after all schedules have the correct enddate
		schedTrigger.setState(TriggerState.ACTIVE);

		// Check that the trigger will fire before adding it, otherwise the scheduler will throw an exception
		// if nextrun not passed in, use now
		Instant now = null;		// DEVNOTE this variable is inappropriately named since it may not always be "now"
		if (lastRun == null) {
			now = Instant.now();
		} else {
			now = lastRun;
		}

		// adjust for xmonths - if the monthly selections are null, it is a weekly schedule
		// and we do not need to do any x month calculations.
		if (frequency.equals(RecurringScheduleFrequencyEnum.MONTHLY)) {
			try {
				int xmonth = Integer.parseInt(xmonths);
				// we do xmonths-1 because we call the cron trigger to compute the next fire time
				// and cron trigger returns a month from the date the we pass in
				now = now.toDateTime(DateTimeZone.forTimeZone(zone)).plusMonths(xmonth-1).toInstant();
			} catch (Exception ex) {
			// so if problem parsing dont add xmonths
			}

		}

		String endTimeString = endTime!=null?endTime.toString():"endTime empty";
		System.out.println("calculateNextExecTime: Weekly by start date: Start Time is " + startTime.toString() + " End time is: " + endTimeString + " Now: " + Instant.now());

		return schedTrigger.computeFireTimeAfter(now);

	}

	public static TimeZone getTimezone(JSONObject o) {
		return TimeZone.getTimeZone(getValueFromJson(o,JSON_TIMEZONE));
	}

	public static CronExpression createCronExpression(Instant startTime, TimeZone zone, RecurringScheduleWeekOfMonthEnum monthlyWeekOfMonthSelection,
		RecurringScheduleDayOfWeekEnum monthlyDayOfWeekSelection, Collection<RecurringScheduleDayOfWeekEnum> weeklyDay) throws ParseException {
		// For simplicity of creating the Cron expression, we will use CronTrigger's existing timezone support
		// and convert it to a LocalDateTime object for the provided TimeZone.
		LocalDateTime localDateTime = startTime.toDateTime(DateTimeZone.forTimeZone(zone)).toLocalDateTime();

		StringBuffer expression = new StringBuffer();
		 //Min (0-59)
		expression.append(localDateTime.getMinuteOfHour());
		expression.append(CRON_PATTERN_SEPERATOR);
		 //Hour (0-23)
		expression.append(localDateTime.getHourOfDay());
		expression.append(CRON_PATTERN_SEPERATOR);

		 //Day of month (1-31)
		if(monthlyWeekOfMonthSelection == null && monthlyDayOfWeekSelection == null && weeklyDay == null ) {
			int dayOfMonth = localDateTime.getDayOfMonth();
			if(dayOfMonth < 29) {
				expression.append(dayOfMonth);
			} else {
				expression.append(CRON_PATTERN_LAST);
			}
		} else {
			expression.append(CRON_PATTERN_ALL);
		}
		expression.append(CRON_PATTERN_SEPERATOR);

		 //Month (1-12).  Monthly recurrence is not done here.
		expression.append(CRON_PATTERN_ALL);
		expression.append(CRON_PATTERN_SEPERATOR);

		//Day of week (0-6)
		if (monthlyDayOfWeekSelection != null && monthlyWeekOfMonthSelection != null) { // monthly week and day
			RecurringScheduleWeekOfMonthEnum week = monthlyWeekOfMonthSelection;
			RecurringScheduleDayOfWeekEnum day = monthlyDayOfWeekSelection;
			if(!week.equals(RecurringScheduleWeekOfMonthEnum.LAST)) {
				expression.append(day.getCode() + "#" + week.getCode());
			} else {
				expression.append(day.getCode() + "#L");
			}
		} else if (weeklyDay != null) {  // this is weekly schedule
			//
			String daysOfWeekExpr=StringUtils.EMPTY;
			for(RecurringScheduleDayOfWeekEnum day : weeklyDay) {
				daysOfWeekExpr += day.toString() + ",";
			}
			// Remove extra comma
			daysOfWeekExpr = daysOfWeekExpr.substring(0, daysOfWeekExpr.length() -1);
			expression.append(daysOfWeekExpr);

		} else {
			expression.append(CRON_PATTERN_ALL);
		}
		expression.append(CRON_PATTERN_SEPERATOR);

		//Year (1970-2099)
		expression.append(CRON_PATTERN_ALL);
		expression.append(CRON_PATTERN_SEPERATOR);
		expression.append(zone.getID());
		return new CronExpression(expression.toString());
	}

	public static Collection<RecurringScheduleDayOfWeekEnum> getWeeklyDaysOfWeek(JSONObject json) {
		try {
			JSONArray daysOfWeek = json.getJSONArray(JSON_WEEKLYDAYSOFWEEK);
			Collection<RecurringScheduleDayOfWeekEnum> result = new LinkedList<>();
			for(int idx = 0; idx < daysOfWeek.length(); idx++) {
				String internalName = (String) daysOfWeek.get(idx);
				RecurringScheduleDayOfWeekEnum day = RecurringScheduleDayOfWeekEnum.valueOf(internalName);
				result.add(day);
			}
			return result;
		} catch (JSONException e) {
			throw new RuntimeException("Unable to read JSONObject");
		}
	}

	public static Collection<RecurringScheduleDayOfWeekEnum> getWeeklyDaysOfWeekUI(JSONObject json) {
		try {
			JSONArray daysOfWeek = json.getJSONArray(RecurringScheduleUtils.JSON_WEEKLYDAYSOFWEEK);
			Collection<RecurringScheduleDayOfWeekEnum> result = new LinkedList<>();
			for(int idx = 0; idx < daysOfWeek.length(); idx++) {
				String internalName = (String) daysOfWeek.get(idx);
				RecurringScheduleDayOfWeekEnum day = RecurringScheduleDayOfWeekEnum.valueOf(internalName);
				result.add(day);
			}
			return result;
		} catch (JSONException e) {
			throw new RuntimeException("Unable to read JSONObject");
		}
	}


	public static int calculateRemaining(Instant instant, JSONObject json) {
		Instant nextRun = instant;
		TimeZone zone = getTimezone(json);
		Instant endTime = getEndTime(json);
		Instant startTime = getStartTime(json);
		RecurringScheduleDayOfWeekEnum monthlyDayOfWeekSelection = null;
		RecurringScheduleWeekOfMonthEnum monthlyWeekOfMonthSelection = null;
		Collection<RecurringScheduleDayOfWeekEnum> weeklyDays = null;
		RecurringScheduleFrequencyEnum frequency = null;
		String internalName = getValueFromJson(json, JSON_FREQUENCY);

		if (StringUtils.isNotEmpty(internalName)) {
			frequency = RecurringScheduleFrequencyEnum.valueOf(internalName);
		}

		// Based on the frequency we populate the enum (monthly or weekly)
		if (RecurringScheduleFrequencyEnum.MONTHLY.equals(frequency)){
			monthlyDayOfWeekSelection = getMonthlyDayOfWeek(json);
			monthlyWeekOfMonthSelection = getMonthlyWeekOfMonth(json);
		} else {
			weeklyDays = getWeeklyDaysOfWeek(json);
		}

		// create a count to add next run dates
		int count = 0;

		//try {
		// loop through days and collect all run dates
		while (endTime != null && (nextRun.isBefore(endTime) || nextRun.isEqual(endTime)) && count <= MAX_NEXTRUN) {

			// add a minute to nextRun to trigger a new date
			Instant i = nextRun.toDateTime().withFieldAdded(DurationFieldType.minutes(), 1).toInstant();

			nextRun = i;
			Instant newRun = calculateNextExecTime(zone, startTime, i, endTime, monthlyWeekOfMonthSelection,
				 monthlyDayOfWeekSelection,	weeklyDays, getXMonths(json), frequency);

			// jump to newRun date
			nextRun = newRun;

			// add date to count
			count++;
		}
		//} catch (NullPointerException npe) {

		//}
		//return finalCount.size();
		return count;
	}
	public static Instant calculateMonthlyWeekDay(TimeZone zone, Instant startTime, Instant lastRun, Instant endTime,
			RecurringScheduleWeekOfMonthEnum monthlyWeekOfMonthSelection, RecurringScheduleDayOfWeekEnum monthlyDayOfWeekSelection,
			String xmonths) {
		Instant exec = null;
		int intXmonths = Integer.parseInt(xmonths);
		Instant nextRun = startTime;
		if (startTime.isBeforeNow() && lastRun == null) {

			// find week/day in
			// add a minute to nextRun to trigger a new date
			Instant i = nextRun.toDateTime().withFieldAdded(DurationFieldType.minutes(), 1).toInstant();
			// get first month and check if we can start on this day.  since our start date may be the first of the month, the "third sunday" may still be
			// startable this month.  Normally we would skip ahead based on start date.
			// try to find a start date in this month
			Instant newRun = calculateNextExecTime(zone, startTime, i, endTime, monthlyWeekOfMonthSelection,
				 monthlyDayOfWeekSelection,	null, "1", RecurringScheduleFrequencyEnum.MONTHLY);

				// if this is the first valid start date and its in the future return it, other wize continue xmonths into future (relative to past)
			if (newRun.isAfterNow() && (newRun.toDateTime().getMonthOfYear() == startTime.toDateTime().getMonthOfYear())) {
			//if (newRun.isAfterNow()) {
				exec = newRun;
			} else {
				if (!((newRun.toDateTime().getMonthOfYear() == startTime.toDateTime().getMonthOfYear())) ){
					//intXmonths --;
					newRun = startTime;
				}
			// so we need to go xmonths and see if its a valid date
				while ((nextRun.isBeforeNow())) {
					for (int j = 0; j < intXmonths; j ++) {
						i = newRun.toDateTime().withFieldAdded(DurationFieldType.minutes(), 1).toInstant();
						newRun = calculateNextExecTime(zone, newRun, i, endTime, monthlyWeekOfMonthSelection,
							 monthlyDayOfWeekSelection,	null, "1", RecurringScheduleFrequencyEnum.MONTHLY);
						String newRunString = newRun!=null?newRun.toString():"newRun empty";
						System.out.println("calculateMonthlyWeekDay Start Time is " + startTime.toString() + " Next Run time: " + newRunString + " Now: " + Instant.now());
					}
					nextRun = newRun;
				}
				exec = nextRun;
			}
		// use current time if start date is in the future
		} else if (startTime.isAfterNow() || startTime.isEqualNow()) {
			// add a minute to nextRun to trigger a new date
			Instant i = nextRun.toDateTime().withFieldAdded(DurationFieldType.minutes(), 1).toInstant();

			// get first month and check if we can start on this day.  since our start date may be the first of the month, the "third sunday" may still be
			// startable this month.  Normally we would skip ahead based on start date.
			// get the next of

			//
			Instant newRun = calculateNextExecTime(zone, startTime, i, endTime, monthlyWeekOfMonthSelection,
					 monthlyDayOfWeekSelection,	null, "1", RecurringScheduleFrequencyEnum.MONTHLY);

				// if this is the first valid start date and its in the future return it, other wize continue xmonths into future (relative to past)
				if (newRun.isAfterNow() && (newRun.toDateTime().getMonthOfYear() == startTime.toDateTime().getMonthOfYear()) ) {
					exec = newRun;
				} else {
				// so we need to go xmonths and see if its a valid date
					if (!((newRun.toDateTime().getMonthOfYear() == startTime.toDateTime().getMonthOfYear()))){
						//intXmonths --;
						newRun = startTime;
					}
					for (int j = 0; j < intXmonths; j ++) {
						i = newRun.toDateTime().withFieldAdded(DurationFieldType.minutes(), 1).toInstant();
						newRun = calculateNextExecTime(zone, newRun, i, endTime, monthlyWeekOfMonthSelection,
							 monthlyDayOfWeekSelection,	null, "1", RecurringScheduleFrequencyEnum.MONTHLY);
						String newRunString = newRun!=null?newRun.toString():"newRun empty";
						System.out.println("calculateMonthlyWeekDay Start Time is " + startTime.toString() + " Next Run time: " + newRunString + " Now: " + Instant.now());
					}
					nextRun = newRun;
					exec = nextRun;
				}
			//

		} else {
			exec = calculateNextExecTime(zone, startTime, lastRun, endTime,	 monthlyWeekOfMonthSelection, monthlyDayOfWeekSelection,
					null, xmonths, RecurringScheduleFrequencyEnum.MONTHLY);
		}

		return exec;
	}
}
