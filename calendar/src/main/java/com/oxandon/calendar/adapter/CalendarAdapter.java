package com.oxandon.calendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.oxandon.calendar.annotation.CalendarStatus;
import com.oxandon.calendar.view.MonthView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peng on 2017/8/3.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final List<Date> dates = new ArrayList<>();

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
        holder.view().value(date, CalendarStatus.NORMAL);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}