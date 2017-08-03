package com.oxandon.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxandon.calendar.R;
import com.oxandon.calendar.annotation.CalendarStatus;
import com.oxandon.calendar.protocol.IDayView;

/**
 * 日期控件
 * Created by peng on 2017/8/2.
 */

public final class DayView extends LinearLayout implements IDayView {
    private TextView tvDesc;
    private TextView tvDay;
    private Integer value = Integer.valueOf(0);
    public final static String TODAY = "今天";

    public DayView(Context context) {
        super(context);
        initialize(context);
    }

    public DayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initialize(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.layout_day_view, this);
        setBackgroundColor(Color.WHITE);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvDay = (TextView) findViewById(R.id.tvDay);
    }

    @Override
    public void desc(String desc, @CalendarStatus int status) {
        tvDesc.setText(desc);
        setTextStatusColor(tvDesc, status);
    }

    @Override
    public void value(Integer index, @CalendarStatus int status) {
        value = index;
        if (index < 0 || index > MAX_DAYS_OF_MONTH) {
            tvDay.setText("");
            desc("", CalendarStatus.NORMAL);
            return;
        }
        tvDay.setText(String.valueOf(index + 1));
        setTextStatusColor(tvDay, status);
    }

    /**
     * 设置文本状态颜色
     *
     * @param tv
     * @param status
     */
    private void setTextStatusColor(TextView tv, @CalendarStatus int status) {
        if (status == CalendarStatus.NORMAL) {
            tv.setTextColor(Color.parseColor("#404040"));
        } else if (status == CalendarStatus.STRESS) {
            tv.setTextColor(Color.parseColor("#FF6600"));
        }
    }

    @Override
    public Integer value() {
        return value;
    }
}