package com.oxandon.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxandon.calendar.R;
import com.oxandon.calendar.annotation.DayStatus;
import com.oxandon.calendar.protocol.IDayView;

/**
 * 日期控件
 * Created by peng on 2017/8/2.
 */

public final class DayView extends LinearLayout implements IDayView {
    private TextView tvDesc;
    private TextView tvDay;
    private Integer value = Integer.valueOf(0);

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
    public void value(Integer index) {
        value = index;
        String content = index < 0 || index > MAX_DAYS_OF_MONTH ? "" : String.valueOf(index + 1);
        tvDay.setText(content);
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public void change(State state) {
        //背景
        setBackgroundStatus(state.status);
        //内容
        setTextStatusColor(tvDay, state.valueStatus);
        //描述
        tvDesc.setText(state.desc);
        setTextStatusColor(tvDesc, state.descStatus);
    }

    /**
     * 设置文本状态颜色
     *
     * @param tv
     * @param status
     */
    private void setTextStatusColor(TextView tv, @DayStatus int status) {
        switch (status) {
            //正常
            case DayStatus.NORMAL:
                //todo
                tv.setTextColor(Color.parseColor("#404040"));
                break;
            //不可用
            case DayStatus.INVALID:
                //todo
                break;
            //范围内
            case DayStatus.RANGE:
                //todo
                break;
            //边界
            case DayStatus.BOUND:
                //todo
                break;
            //强调
            case DayStatus.STRESS:
                //todo
                tv.setTextColor(Color.parseColor("#FF6600"));
                break;
        }
    }

    /**
     * 设置背景状态
     *
     * @param status
     */
    private void setBackgroundStatus(@DayStatus int status) {
        switch (status) {
            //正常
            case DayStatus.NORMAL:
                //todo
                setEnabled(true);
                break;
            //不可用
            case DayStatus.INVALID:
                //todo
                setEnabled(false);
                break;
            //范围内
            case DayStatus.RANGE:
                //todo
                setEnabled(true);
                break;
            //边界
            case DayStatus.BOUND:
                //todo
                setEnabled(true);
                break;
            //强调
            case DayStatus.STRESS:
                //todo
                setEnabled(true);
                break;
        }
    }
}