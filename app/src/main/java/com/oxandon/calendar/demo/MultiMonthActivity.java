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
//        calendarView.getAdapter().valid("2016-08-09", "2016-09-05");
//        calendarView.getAdapter().valid(null, null);
//        calendarView.getAdapter().valid(null, "2016-09-05");
//        calendarView.getAdapter().valid("2016-09-05", null);

        calendarView.getAdapter().select("2016-08-12", "2016-08-22");
        calendarView.show("2016-08-01", "2019-08-01", "yyyy-MM-dd");
    }
}