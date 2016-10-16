package example;


public enum RecurringScheduleWeekOfMonthEnum {

	FIRST(1),
	SECOND(2),
	THIRD(3),
	FOURTH(4),
	LAST(5);

	private final int code;

	RecurringScheduleWeekOfMonthEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
