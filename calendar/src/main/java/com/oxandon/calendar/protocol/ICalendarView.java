package com.oxandon.calendar.protocol;

import android.support.annotation.NonNull;

import com.oxandon.calendar.annotation.CalendarStatus;

/**
 * Created by peng on 2017/8/2.
 */

public interface ICalendarView<T> {
    int MAX_DAYS_OF_MONTH = 31;
    int WEEK_DAYS = 7;
    int MAX_HORIZONTAL_LINES = 6;

    /**
     * 设置描述
     *
     * @param desc
     */
    void desc(String desc, @CalendarStatus int status);

    /**
     * 设置值
     *
     * @param value
     */
    void value(@NonNull T value, @CalendarStatus int status);

    /**
     * 获取值
     *
     * @return
     */
    T value();
}