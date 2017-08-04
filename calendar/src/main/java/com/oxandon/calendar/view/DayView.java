package com.oxandon.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxandon.calendar.R;
import com.oxandon.calendar.annotation.DayStatus;
import com.oxandon.calendar.protocol.DayEntity;

/**
 * 日期控件
 * Created by peng on 2017/8/2.
 */

public final class DayView extends LinearLayout {
    private TextView tvDesc;
    private TextView tvDay;
    private DayEntity entity;

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

    public void value(DayEntity entity) {
        if (null != value()) {
            value().recycle();
        }
        this.entity = entity;
        //背景
        setBackgroundStatus(entity);
        //内容
        setTextStatusColor(tvDay, entity.valueStatus());
        tvDay.setText(entity.value());
        //描述
        tvDesc.setText(entity.desc());
        setTextStatusColor(tvDesc, entity.descStatus());
    }

    public DayEntity value() {
        return entity;
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
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.common_text_color));
                break;
            //不可用
            case DayStatus.INVALID:
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.invalid_text_color));
                break;
            //范围内
            case DayStatus.RANGE:
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.select_text_color));
                break;
            //左边界
            case DayStatus.BOUND_L:
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.select_text_color));
                break;
            //右边界
            case DayStatus.BOUND_R:
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.select_text_color));
                break;
            //强调
            case DayStatus.STRESS:
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.stress_text_color));
                break;
        }
    }

    /**
     * 设置背景状态
     *
     * @param entity
     */
    private void setBackgroundStatus(DayEntity entity) {
        switch (entity.status()) {
            //正常
            case DayStatus.NORMAL:
                setBackgroundColor(ContextCompat.getColor(getContext(), R.color.common_bg_color));
                setEnabled(true);
                break;
            //不可用
            case DayStatus.INVALID:
                setBackgroundColor(ContextCompat.getColor(getContext(), R.color.common_bg_color));
                setTextStatusColor(tvDay, entity.status());
                setEnabled(false);
                break;
            //范围内
            case DayStatus.RANGE:
                setBackgroundColor(ContextCompat.getColor(getContext(), R.color.range_bg_color));
                setEnabled(true);
                break;
            //左边界
            case DayStatus.BOUND_L:
                setBackgroundResource(R.drawable.range_lbg);
                break;
            //右边界
            case DayStatus.BOUND_R:
                setBackgroundResource(R.drawable.range_rbg);
                break;
            //强调
            case DayStatus.STRESS:
                setBackgroundColor(ContextCompat.getColor(getContext(), R.color.range_bg_color));
                setEnabled(true);
                break;
        }
    }
}