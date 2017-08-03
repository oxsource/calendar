package com.oxandon.calendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.oxandon.calendar.protocol.IMonthView;
import com.oxandon.calendar.utils.TimeUtil;
import com.oxandon.calendar.view.MonthView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peng on 2017/8/3.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final List<Date> dates = new ArrayList<>();
    private IMonthView.Interval interval = new IMonthView.Interval();

    public CalendarAdapter() {
    }

    public void update(List<Date> list) {
        if (null != list && list.size() > 0) {
            dates.addAll(list);
        } else {
            dates.clear();
        }
        notifyDataSetChanged();
    }

    public void valid(String fromDay, String toDay) {
        try {
            Date from = TimeUtil.date(fromDay, TimeUtil.YY_MD);
            Date to = TimeUtil.date(toDay, TimeUtil.YY_MD);
            interval.left = from;
            interval.right = to;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MonthView view = new MonthView(parent.getContext());
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        view.setLayoutParams(params);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        Date date = dates.get(position);
        holder.view().value(date);
        holder.view().valid(interval);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public Date value(int position) {
        if (position >= 0 && position < dates.size()) {
            return dates.get(position);
        }
        return new Date(0);
    }
}