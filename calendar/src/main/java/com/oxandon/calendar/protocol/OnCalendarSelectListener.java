package com.oxandon.calendar.protocol;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * 日历选择监听器
 * Created by peng on 2017/8/4.
 */

public interface OnCalendarSelectListener {
    void onCalendarSingleSelect(@NonNull Date date);

    void onCalendarBothSelect(@NonNull Date before, @NonNull Date after);
}