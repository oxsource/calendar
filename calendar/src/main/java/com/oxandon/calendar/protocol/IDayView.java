package com.oxandon.calendar.protocol;

import com.oxandon.calendar.annotation.DayStatus;

/**
 * Created by peng on 2017/8/2.
 */

public interface IDayView extends ICalendarView<Integer> {
    class State {
        @DayStatus
        public int status;
        @DayStatus
        public int valueStatus;
        public String desc;
        @DayStatus
        public int descStatus;
    }

    void change(State state);
}