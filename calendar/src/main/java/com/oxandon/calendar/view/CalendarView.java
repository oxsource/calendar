package com.oxandon.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.oxandon.calendar.R;
import com.oxandon.calendar.adapter.CalendarAdapter;
import com.oxandon.calendar.utils.DateUtils;
import com.oxandon.calendar.utils.TimeUtil;
import com.oxandon.calendar.utils.ViewUtils;
import com.oxandon.calendar.view.decoration.GroupListener;
import com.oxandon.calendar.view.decoration.StickyDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日历控件
 * Created by peng on 2017/8/3.
 */

public class CalendarView extends LinearLayout {
    private final CalendarAdapter calendarAdapter = new CalendarAdapter();

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        inflate(context, R.layout.layout_calendar_view, this);
        //初始化星期标头
        initWeekGridView(context);
        //月份列表
        RecyclerView bodyView = (RecyclerView) findViewById(R.id.bodyView);
        bodyView.setLayoutManager(new LinearLayoutManager(context));
        bodyView.setAdapter(getAdapter());
        initDecoration(bodyView);
    }

    private void initWeekGridView(Context context) {
        String[] from = new String[]{"week"};
        int[] to = new int[]{R.id.tvWeekDay};
        String[] strings = new String[]{
                "日", "一", "二", "三", "四", "五", "六"
        };
        List<Map<String, String>> weeks = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put(from[0], strings[i]);
            weeks.add(map);
        }
        ListAdapter adapter = new SimpleAdapter(context, weeks, R.layout.layout_week_view, from, to);
        GridView weekView = (GridView) findViewById(R.id.weekView);
        weekView.setNumColumns(adapter.getCount());
        weekView.setAdapter(adapter);
        weekView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    public void initDecoration(RecyclerView bodyView) {
        GroupListener groupListener = new GroupListener() {
            @Override
            public String getGroupName(int position) {
                Date date = getAdapter().value(position);
                return TimeUtil.dateText(date.getTime(), TimeUtil.YY_M_CN);
            }
        };
        StickyDecoration decoration = StickyDecoration.Builder
                .init(groupListener)
                .setGroupBackground(ContextCompat.getColor(getContext(), R.color.common_bg_color))     //背景色
                .setGroupHeight(ViewUtils.dp2px(getContext(), 40))     //高度
                .setDivideColor(ContextCompat.getColor(getContext(), R.color.divide_line_color))           //分割线颜色
                .setDivideHeight(ViewUtils.dp2px(getContext(), 0.5f))  //分割线高度 (默认没有分割线)
                .setGroupTextColor(ContextCompat.getColor(getContext(), R.color.common_text_color))                        //字体颜色
                .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD)) //加粗
                .setGroupTextSize(ViewUtils.sp2px(getContext(), 15))   //字体大小
                .setTextSideMargin(ViewUtils.dp2px(getContext(), 10))  //边距   靠左时为左边距  靠右时为右边距
                .setTextAlign(Paint.Align.CENTER)                      //居中显示
                .build();
        bodyView.addItemDecoration(decoration);
    }

    public void show(Date sDate, Date eDate) {
        List<Date> dates = DateUtils.fillMonths(sDate, eDate);
        getAdapter().update(dates);
    }

    public void show(String sTime, String eTime, String format) {
        Date[] dates = new Date[2];
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dates[0] = sdf.parse(sTime);
            dates[1] = sdf.parse(eTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        show(dates[0], dates[1]);
    }

    public CalendarAdapter getAdapter() {
        return calendarAdapter;
    }
}