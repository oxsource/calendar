package com.oxandon.calendar.protocol;

import com.oxandon.calendar.annotation.DayStatus;

import java.util.Date;

/**
 * Created by peng on 2017/8/2.
 */

public interface IMonthView extends ICalendarView<Date> {
    class State {
        public Date a;
        public Date b;
        @DayStatus
        public int status;
    }
}
