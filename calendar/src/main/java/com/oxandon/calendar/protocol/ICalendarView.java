package com.oxandon.calendar.protocol;

import android.support.annotation.NonNull;

/**
 * Created by peng on 2017/8/2.
 */

public interface ICalendarView<T> {
    int MAX_DAYS_OF_MONTH = 31;
    int WEEK_DAYS = 7;
    int MAX_HORIZONTAL_LINES = 6;

    String STR_TODAY = "今天";


    /**
     * 设置值
     *
     * @param value
     */
    void value(@NonNull T value);

    /**
     * 获取值
     *
     * @return
     */
    T value();
}