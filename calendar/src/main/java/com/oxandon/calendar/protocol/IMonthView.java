package com.oxandon.calendar.protocol;

import java.util.Date;

/**
 * Month视图接口
 * Created by peng on 2017/8/2.
 */

public interface IMonthView extends ICalendarView<Date> {
    int WEEK_DAYS = 7;

    int MAX_HORIZONTAL_LINES = 6;

    String STR_TODAY = "今天";

    /**
     * 日期区间
     */
    class Interval {
        public Date left;
        public Date right;
    }

    /**
     * 选中操作
     *
     * @param interval
     */
    void select(Interval interval);

    /**
     * 合法化操作
     *
     * @param interval
     */
    void valid(Interval interval);
}