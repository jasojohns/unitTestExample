package example;

import java.util.Arrays;
import java.util.Collection;

public enum RecurringScheduleDayOfWeekEnum  {

	SUNDAY(0),
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6);

	private final int code;

	RecurringScheduleDayOfWeekEnum(int code) {
		this.code = code;
	}

	public static RecurringScheduleDayOfWeekEnum[] ORDERED_DAYS_OF_WEEK = new RecurringScheduleDayOfWeekEnum[] {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY};

	public static Collection<RecurringScheduleDayOfWeekEnum> getDaysOfWeekNoBlank() {
		return Arrays.asList(ORDERED_DAYS_OF_WEEK);
	}

	public int getCode() {
		return code;
	}

}
