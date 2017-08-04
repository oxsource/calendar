package com.oxandon.calendar.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.oxandon.calendar.utils.TimeUtil;

import java.util.Date;

public class MainActivity extends Activity implements OnCalendarPopSelectListener {

    private CalendarPopWindow mCalendarPopWindow;
    private TextView mTextView;
    private Date beforeDate;
    private Date afterDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_result);
        mCalendarPopWindow = new CalendarPopWindow(this, this);
    }

    public void onPopClickMulti(View v) {
        if (beforeDate != null && afterDate != null) {
            mCalendarPopWindow.onShow(v, beforeDate, afterDate);
        } else {
            mCalendarPopWindow.onShow(v);
        }
    }

    @Override
    public void onSelect(@NonNull Date before, @NonNull Date after) {
        beforeDate = before;
        afterDate = after;
        mTextView.setText("选择的日期是" + TimeUtil.dateText(before.getTime(), TimeUtil.YY_MD) + "至" + TimeUtil.dateText(after
                .getTime(), TimeUtil.YY_MD));
    }
}
