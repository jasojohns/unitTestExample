package example;

import java.util.Arrays;
import java.util.List;

public enum RecurringScheduleFrequencyEnum {

	ONCE(0, OneTimeScheduledEvent.class),
	DAILY(1, DailyRecurringScheduledEvent.class),
	WEEKLY(2, WeeklyRecurringScheduledEvent.class),
	MONTHLY(3, MonthlyRecurringScheduledEvent.class),
	YEARLY(4, YearlyRecurringScheduledEvent.class);

	private final Class<? extends ScheduledEvent> scheduledEventType;

	private final int code;

	RecurringScheduleFrequencyEnum(int code, Class<? extends ScheduledEvent> scheduledEventType) {
		this.code = code;
		this.scheduledEventType = scheduledEventType;
	}

	public boolean isRecurring() {
		return getAllFrequenciesThatRecur().contains(this);
	}

	/**
	 * @return the scheduledEventType
	 */
	public Class<? extends ScheduledEvent> getScheduledEventType() {
		return scheduledEventType;
	}

	private static RecurringScheduleFrequencyEnum[] ALL_FREQUENCIES_THAT_RECUR = new RecurringScheduleFrequencyEnum[] { DAILY, WEEKLY, MONTHLY, YEARLY };
	public static List<RecurringScheduleFrequencyEnum> getAllFrequenciesThatRecur() {
		return Arrays.asList(ALL_FREQUENCIES_THAT_RECUR);
	}

	/* Didn't put the new YEARLY option in this list to avoid introducing an option that wasn't originally designed for in existing call sites. jjohns 1/12/2016
	 */
	private static RecurringScheduleFrequencyEnum[] FREQUENCIES_THAT_RECUR = new RecurringScheduleFrequencyEnum[] { DAILY, WEEKLY, MONTHLY };
	public static List<RecurringScheduleFrequencyEnum> getFrequenciesThatRecur() {
		return Arrays.asList(FREQUENCIES_THAT_RECUR);
	}

	private static RecurringScheduleFrequencyEnum[] TWO_FREQUENCIES_THAT_RECUR = new RecurringScheduleFrequencyEnum[] { WEEKLY, MONTHLY };
	public static List<RecurringScheduleFrequencyEnum> getTwoFrequenciesThatRecur() {
		return Arrays.asList(TWO_FREQUENCIES_THAT_RECUR);
	}

}
