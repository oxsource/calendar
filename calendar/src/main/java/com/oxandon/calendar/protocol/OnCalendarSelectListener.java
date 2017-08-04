package com.oxandon.calendar.protocol;

import java.util.Date;

/**
 * 日历选择监听器
 * Created by peng on 2017/8/4.
 */

public interface OnCalendarSelectListener {
    void onCalendarSelect(Interval<Date> dateInterval);
}