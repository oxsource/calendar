package com.oxandon.calendar.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oxandon.calendar.view.CalendarView;

/**
 * Created by peng on 2017/8/2.
 */

public class MultiMonthActivity extends Activity {
    private CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_month);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.func();
    }
}