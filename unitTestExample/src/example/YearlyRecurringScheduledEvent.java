package example;

import org.joda.time.MutableDateTime;

public class YearlyRecurringScheduledEvent extends MonthlyRecurringScheduledEvent {

	private RecurringScheduleMonthsEnum monthOfYear;

	/**
	 * @return the monthOfYear
	 */
	public RecurringScheduleMonthsEnum getMonthOfYear() {
		return monthOfYear;
	}

	/**
	 * @param monthOfYear the monthOfYear to set
	 */
	public void setMonthOfYear(RecurringScheduleMonthsEnum monthOfYear) {
		this.monthOfYear = monthOfYear;
	}


	@Override
	public RecurringScheduleFrequencyEnum getScheduleFrequency() {
		return RecurringScheduleFrequencyEnum.YEARLY;
	}

	@Override
	protected void setBeginningData(MutableDateTime dateTime) {
		dateTime.setMonthOfYear(monthOfYear.getCode());
	}

	@Override
	protected void addTime(MutableDateTime dateTime, int numberOfUnits) {
		dateTime.addYears(numberOfUnits);
	}
}
