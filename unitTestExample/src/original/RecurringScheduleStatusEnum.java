package original;


public enum RecurringScheduleStatusEnum {

	INACTIVE(1),
	ACTIVE(0);

	private final int code;

	RecurringScheduleStatusEnum(int code) {
		this.code = code;
	}

}
