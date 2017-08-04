package com.oxandon.calendar.demo;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * 日历Popwindow选择监听器
 * Created by wangcheng on 2017/8/4.
 */

public interface OnCalendarPopSelectListener {
    void onSelect(@NonNull Date before, @NonNull Date after);
}
