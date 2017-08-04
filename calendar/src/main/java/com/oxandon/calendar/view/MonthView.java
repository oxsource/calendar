package com.oxandon.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.oxandon.calendar.R;
import com.oxandon.calendar.annotation.DayStatus;
import com.oxandon.calendar.protocol.DayEntity;
import com.oxandon.calendar.protocol.MonthEntity;
import com.oxandon.calendar.utils.DateUtils;
import com.oxandon.calendar.utils.ViewUtils;

/**
 * 月份控件
 * Created by peng on 2017/8/2.
 */

public class MonthView extends ViewGroup {
    private final DayView[] dayViews = new DayView[MonthEntity.MAX_DAYS_OF_MONTH];
    private final View[] lines = new View[MonthEntity.MAX_HORIZONTAL_LINES];
    private final int LINE_HEIGHT;

    private MonthEntity entity;
    private int isTodayOfMonth = -1;
    //location
    private int position = 0;
    private int offset = 0;
    //child width and height
    private int childWidth = 0;
    private int childHeight = 0;
    //row count
    private int dayRows = 0;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.WHITE);
        //DayView
        for (int i = 0; i < dayViews.length; i++) {
            dayViews[i] = new DayView(context);
            addView(dayViews[i]);
        }
        //horizontal line
        LINE_HEIGHT = ViewUtils.dp2px(context, 0.5f);
        for (int j = 0; j < lines.length; j++) {
            View view = new View(getContext());
            view.setBackgroundResource(R.color.divide_line_color);
            addView(view);
            lines[j] = view;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (null == value()) {
            return;
        }
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        dayViews[0].measure(widthMeasureSpec, heightMeasureSpec);
        int childrenHeight = 0;
        //calc need rows
        int amount = position + offset;
        dayRows = (amount / MonthEntity.WEEK_DAYS) + (((amount % MonthEntity.WEEK_DAYS) != 0) ? 1 : 0);
        //measure container
        childrenHeight += dayViews[0].getMeasuredHeight() * dayRows;
        childrenHeight += (dayRows) * LINE_HEIGHT;
        setMeasuredDimension(totalWidth, childrenHeight);
        //measure DayViews
        childWidth = totalWidth / MonthEntity.WEEK_DAYS;
        childHeight = dayViews[0].getMeasuredHeight();
        int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
        int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
        for (int i = 0; i < dayViews.length; i++) {
            dayViews[i].measure(childWidthSpec, childHeightSpec);
        }
        //measure horizontal lines
        for (int i = 0; i < lines.length; i++) {
            lines[i].measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(LINE_HEIGHT, MeasureSpec.EXACTLY));
        }
    }

    //分割线布局控制器
    private static class SplitLinesLayoutControl {
        private final int width;
        private final int height;
        private int count = 0;
        private View[] view;

        public SplitLinesLayoutControl(@NonNull View[] views) {
            this.view = views;
            width = views[0].getMeasuredWidth();
            height = views[0].getMeasuredHeight();
        }

        public int layout(int offsetY) {
            if (count >= view.length) {
                return offsetY;
            }
            int bottom = offsetY + height;
            view[count].layout(0, offsetY, width, bottom);
            count += 1;
            return bottom;
        }
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        if (null == value()) {
            return;
        }
        int offsetX = 0, offsetY = 0;
        SplitLinesLayoutControl lineControl = new SplitLinesLayoutControl(lines);
        for (int i = 0; i < position; i++) {
            offsetX += childWidth;
        }
        int childBottom = offsetY + childHeight;
        boolean lastIsRightBound = false;//上一个是否是右边界

        int[] validRange = DateUtils.containDaysIndex(value().date, entity.valid.left, entity.valid.right);
//        int[] selectRange = DateUtils.containDaysIndex(value().date, entity.select.left, entity.select.right);

        for (int index = 0, move = position + 1; index < dayViews.length; index++, move++) {
            boolean rightBound = move % MonthEntity.WEEK_DAYS == 0;
            DayEntity entity;
            if (index < offset) {
                //set state
                boolean isToday = index == isTodayOfMonth;
                entity = DayEntity.obtain(DayStatus.NORMAL, index, isToday ? MonthEntity.STR_TODAY : "");
                entity.valueStatus = (lastIsRightBound || rightBound) ? DayStatus.STRESS : DayStatus.NORMAL;
                entity.descStatus = isToday ? DayStatus.STRESS : DayStatus.NORMAL;
                //valid
                boolean contain = (index >= validRange[0]) && (index <= validRange[1]);
                if (contain) {
                    //select
//                    if ((index > selectRange[0]) && (index < selectRange[1])) {
                    //in range
//                    } else if (index == selectRange[0]) {
                    //
//                    } else if (index == selectRange[1]) {

//                    }
                } else {
                    //不响应选择事件
                    entity.status = DayStatus.INVALID;
                    entity.valueStatus = DayStatus.INVALID;
                    entity.descStatus = DayStatus.INVALID;
                }
            } else {
                entity = DayEntity.obtain(DayStatus.INVALID, -1, "");
            }
            dayViews[index].value(entity);
            dayViews[index].layout(offsetX, offsetY, offsetX + childWidth, childBottom);
            if (rightBound) {
                offsetX = 0;
                offsetY += childHeight;
                //draw horizontal line
                offsetY = lineControl.layout(offsetY);
                childBottom = offsetY + childHeight;
            } else {
                offsetX += childWidth;
            }
            lastIsRightBound = rightBound;
        }
        lineControl.layout(offsetY + childHeight);
    }

    public void value(@NonNull MonthEntity entity) {
        if (null != value()) {
            value().recycle();
        }
        this.entity = entity;
        position = DateUtils.firstDayOfMonthIndex(entity.date);
        offset = DateUtils.maxDaysOfMonth(entity.date);
        isTodayOfMonth = DateUtils.isTodayOfMonth(entity.date);
        requestLayout();
    }

    public MonthEntity value() {
        return entity;
    }
}