package com.oxandon.calendar.protocol;

import com.oxandon.calendar.annotation.DayStatus;

/**
 * Day视图接口
 * Created by peng on 2017/8/2.
 */

public interface IDayView extends ICalendarView<Integer> {
    /**
     * 状态信息
     */
    class State {
        @DayStatus
        public int status;
        @DayStatus
        public int valueStatus;
        public String desc;
        @DayStatus
        public int descStatus;
    }

    /**
     * 状态改变
     *
     * @param state
     */
    void change(State state);
}