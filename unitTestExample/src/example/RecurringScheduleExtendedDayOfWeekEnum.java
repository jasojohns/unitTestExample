package example;

import org.joda.time.DateTimeConstants;


public class RecurringScheduleExtendedDayOfWeekEnum  {

	public static final  RecurringScheduleExtendedDayOfWeekEnum SUNDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.SUNDAY.getCode());
	public static final  RecurringScheduleExtendedDayOfWeekEnum MONDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.MONDAY.getCode());
	public static final  RecurringScheduleExtendedDayOfWeekEnum TUESDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.TUESDAY.getCode());
	public static final  RecurringScheduleExtendedDayOfWeekEnum WEDNESDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.WEDNESDAY.getCode());
	public static final  RecurringScheduleExtendedDayOfWeekEnum THURSDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.THURSDAY.getCode());
	public static final  RecurringScheduleExtendedDayOfWeekEnum FRIDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.FRIDAY.getCode());
	public static final  RecurringScheduleExtendedDayOfWeekEnum SATURDAY = new RecurringScheduleExtendedDayOfWeekEnum(RecurringScheduleDayOfWeekEnum.SATURDAY.getCode());

	public static final  RecurringScheduleExtendedDayOfWeekEnum DAY = new RecurringScheduleExtendedDayOfWeekEnum(7);
	public static final  RecurringScheduleExtendedDayOfWeekEnum WEEK_DAY = new RecurringScheduleExtendedDayOfWeekEnum(8);
	public static final  RecurringScheduleExtendedDayOfWeekEnum WEEKEND_DAY = new RecurringScheduleExtendedDayOfWeekEnum(9);

	private final int code;

	public RecurringScheduleExtendedDayOfWeekEnum(int code) {
		this.code = code;
	}

	public int getJodaDateTimeConstantForDayOfWeekEnum() {
		if (this == MONDAY) {
			return DateTimeConstants.MONDAY;
		} else if (this == TUESDAY) {
			return DateTimeConstants.TUESDAY;
		} else if (this == WEDNESDAY) {
			return DateTimeConstants.WEDNESDAY;
		} else if (this == THURSDAY) {
			return DateTimeConstants.THURSDAY;
		} else if (this == FRIDAY) {
			return DateTimeConstants.FRIDAY;
		} else if (this == SATURDAY) {
			return DateTimeConstants.SATURDAY;
		} else if (this == SUNDAY) {
			return DateTimeConstants.SUNDAY;
		}

		return 0;
	}

}
