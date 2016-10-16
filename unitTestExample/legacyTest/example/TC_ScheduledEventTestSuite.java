package example;

import junit.framework.Test;
import junit.framework.TestSuite;
import example.monthly.TC_MonthlyEventDayOfMonth;
import example.monthly.TC_MonthlyEventDayOfWeek;
import example.monthly.TC_MonthlyEventEveryFriday;
import example.monthly.TC_MonthlyEventEveryMonday;
import example.monthly.TC_MonthlyEventEveryThursday;
import example.monthly.TC_MonthlyEventEveryTuesday;
import example.monthly.TC_MonthlyEventEveryWednesday;
import example.monthly.TC_MonthlyEventWeekDayOfWeek;
import example.monthly.TC_MonthlyEventWeekendDayOfWeek;
import example.yearly.TC_YearlyEventDayOfMonth;
import example.yearly.TC_YearlyEventEveryFriday;
import example.yearly.TC_YearlyEventEveryMonday;
import example.yearly.TC_YearlyEventEveryThursday;
import example.yearly.TC_YearlyEventEveryTuesday;
import example.yearly.TC_YearlyEventEveryWednesday;
import example.yearly.TC_YearlyEventWeekDayOfWeek;
import example.yearly.TC_YearlyEventWeekendDayOfWeek;

public class TC_ScheduledEventTestSuite extends TestSuite {

	TC_ScheduledEventTestSuite() {
        super(
        	TC_OneTimeEvent.class,
        	TC_DailyEvent.class,
        	TC_WeeklyEvent.class,
        	TC_MonthlyEventDayOfMonth.class,
        	TC_MonthlyEventDayOfWeek.class,
        	TC_MonthlyEventWeekDayOfWeek.class,
        	TC_MonthlyEventWeekendDayOfWeek.class,
        	TC_MonthlyEventEveryMonday.class,
        	TC_MonthlyEventEveryTuesday.class,
        	TC_MonthlyEventEveryWednesday.class,
        	TC_MonthlyEventEveryThursday.class,
        	TC_MonthlyEventEveryFriday.class,
        	TC_YearlyEventDayOfMonth.class,
        	TC_YearlyEventWeekDayOfWeek.class,
        	TC_YearlyEventWeekendDayOfWeek.class,
        	TC_YearlyEventEveryMonday.class,
        	TC_YearlyEventEveryTuesday.class,
        	TC_YearlyEventEveryWednesday.class,
        	TC_YearlyEventEveryThursday.class,
        	TC_YearlyEventEveryFriday.class,

        	TC_OutlookStyleStartForAPL_6157.class);
    }

    public static Test suite() {
        return new TC_ScheduledEventTestSuite();
    }

}
