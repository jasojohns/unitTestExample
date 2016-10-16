package example;


public class RecurringScheduleMonthsEnum  {

	public static final RecurringScheduleMonthsEnum JANUARY = new RecurringScheduleMonthsEnum(Month.JAN);
	public static final RecurringScheduleMonthsEnum FEBURARY = new RecurringScheduleMonthsEnum(Month.FEB);
	public static final RecurringScheduleMonthsEnum MARCH = new RecurringScheduleMonthsEnum(Month.MAR);
	public static final RecurringScheduleMonthsEnum APRIL = new RecurringScheduleMonthsEnum(Month.APR);
	public static final RecurringScheduleMonthsEnum MAY = new RecurringScheduleMonthsEnum(Month.MAY);
	public static final RecurringScheduleMonthsEnum JUNE = new RecurringScheduleMonthsEnum(Month.JUN);
	public static final RecurringScheduleMonthsEnum JULY = new RecurringScheduleMonthsEnum(Month.JUL);
	public static final RecurringScheduleMonthsEnum AUGUST = new RecurringScheduleMonthsEnum(Month.AUG);
	public static final RecurringScheduleMonthsEnum SEPTEMBER = new RecurringScheduleMonthsEnum(Month.SEP);
	public static final RecurringScheduleMonthsEnum OCTOBER = new RecurringScheduleMonthsEnum(Month.OCT);
	public static final RecurringScheduleMonthsEnum NOVEMBER = new RecurringScheduleMonthsEnum(Month.NOV);
	public static final RecurringScheduleMonthsEnum DECEMBER = new RecurringScheduleMonthsEnum(Month.DEC);

	private final Month month;

	protected RecurringScheduleMonthsEnum(Month monthEnum) {
		this.month = monthEnum;
	}

	public int getCode() {
		return month.getCode();
	}
}
