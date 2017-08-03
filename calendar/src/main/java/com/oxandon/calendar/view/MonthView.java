package com.oxandon.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oxandon.calendar.annotation.CalendarStatus;
import com.oxandon.calendar.date.DateUtils;
import com.oxandon.calendar.protocol.ICalendar;
import com.oxandon.calendar.protocol.IMonthView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peng on 2017/8/2.
 */

public class MonthView extends ViewGroup implements IMonthView {
    private final int HORIZONTAL_LINES = 6;
    private final DayView[] dayViews = new DayView[MAX_DAYS_OF_MONTH];
    private final View[] lines = new View[HORIZONTAL_LINES];
    private TextView tvHead;
    //dimens
    private final int HEAD_HEIGHT;
    private final int LINE_HEIGHT;

    private Date value;
    private int isTodayOfMonth = -1;
    //location
    private int position = 0;
    private int offset = 0;
    //child width and height
    private int childWidth = 0;
    private int childHeight = 0;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        HEAD_HEIGHT = ViewUtils.dp2px(context, 30);
        LINE_HEIGHT = ViewUtils.dp2px(context, 0.5f);
        //head text view
        TextView head = new TextView(getContext());
        head.setBackgroundColor(Color.parseColor("#90CFCFCF"));
        head.setGravity(Gravity.CENTER);
        head.setTextSize(15);
        head.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        addView(head);
        tvHead = head;
        //DayView
        for (int i = 0; i < dayViews.length; i++) {
            dayViews[i] = new DayView(context);
            addView(dayViews[i]);
        }
        //horizontal line
        for (int j = 0; j < lines.length; j++) {
            View view = new View(getContext());
            view.setBackgroundColor(Color.argb(100, 50, 50, 50));
            addView(view);
            lines[j] = view;
        }
        value(new Date(), CalendarStatus.NORMAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        dayViews[0].measure(widthMeasureSpec, heightMeasureSpec);
        int childrenHeight = HEAD_HEIGHT;
        childrenHeight += dayViews[0].getMeasuredHeight() * MAX_ROWS;
        childrenHeight += HORIZONTAL_LINES * LINE_HEIGHT;
        setMeasuredDimension(totalWidth, childrenHeight);
        //measure head text view
        tvHead.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(HEAD_HEIGHT, MeasureSpec.EXACTLY));
        //measure DayViews
        childWidth = totalWidth / WEEK_DAYS;
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
        int offsetX = 0, offsetY = 0;
        tvHead.layout(0, offsetY, getMeasuredWidth(), offsetY + tvHead.getMeasuredHeight());
        offsetY += tvHead.getMeasuredHeight();

        SplitLinesLayoutControl lineControl = new SplitLinesLayoutControl(lines);
        for (int i = 0; i < position; i++) {
            offsetX += childWidth;
        }
        offsetY = lineControl.layout(offsetY);
        int childBottom = offsetY + childHeight;
        boolean lastIsRightBound = false;//上一个是否是右边界
        for (int index = 0, move = position + 1; index < dayViews.length; index++, move++) {
            boolean rightBound = move % 7 == 0;
            if (index < offset) {
                int status = (lastIsRightBound || rightBound) ? CalendarStatus.STRESS : CalendarStatus.NORMAL;
                dayViews[index].value(Integer.valueOf(index), status);
                boolean isToday = index == isTodayOfMonth;
                dayViews[index].desc(isToday ? DayView.TODAY : "", isToday ? CalendarStatus.STRESS : CalendarStatus.NORMAL);
            } else {
                dayViews[index].value(Integer.valueOf(-1), CalendarStatus.NORMAL);
            }
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

    @Override
    public void desc(String desc, int status) {
        tvHead.setText(desc);
    }

    @Override
    public void value(@NonNull Date date, int status) {
        position = calendar().firstDayOfMonthIndex(date);
        offset = calendar().maxDaysOfMonth(date);
        isTodayOfMonth = calendar().isTodayOfMonth(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        desc(sdf.format(date), CalendarStatus.NORMAL);
        if (null != value()) {
            requestLayout();
        }
        value = date;
    }

    @Override
    public Date value() {
        return value;
    }

    private ICalendar calendar() {
        return DateUtils.get();
    }
}