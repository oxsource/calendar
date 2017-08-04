package com.oxandon.calendar.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.oxandon.calendar.protocol.Interval;
import com.oxandon.calendar.protocol.MonthEntity;
import com.oxandon.calendar.protocol.OnCalendarSelectListener;
import com.oxandon.calendar.protocol.OnMonthClickListener;
import com.oxandon.calendar.utils.TimeUtil;
import com.oxandon.calendar.view.MonthView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peng on 2017/8/3.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> implements OnMonthClickListener {
    private final String TAG = CalendarAdapter.class.getSimpleName();
    private final List<Date> dates = new ArrayList<>();
    private Interval<Date> valid = new Interval<>();
    private Interval<Date> select = new Interval<>();
    private Interval<String> selectNote = new Interval<>();

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
            valid.left(from);
        } catch (Exception e) {
            valid.left(null);
        }
        try {
            Date to = TimeUtil.date(toDay, TimeUtil.YY_MD);
            valid.right(to);
        } catch (Exception e) {
            valid.right(null);
        }
        notifyDataSetChanged();
    }

    /**
     * 选择区间提示语
     *
     * @param noteFrom
     * @param noteTo
     */
    public void intervalNotes(String noteFrom, String noteTo) {
        selectNote.left(noteFrom).right(noteTo);
    }

    /**
     * 设置选择范围
     *
     * @param fromDay
     * @param toDay
     */
    public void select(String fromDay, String toDay) {
        try {
            Date from = TimeUtil.date(fromDay, TimeUtil.YY_MD);
            Date to = TimeUtil.date(toDay, TimeUtil.YY_MD);
            select(from, to);
        } catch (Exception e) {
            select.left(null);
            select.right(null);
        }
    }

    /**
     * 设置选择范围
     *
     * @param from
     * @param to
     */
    public void select(Date from, Date to) {
        select.left(from).right(to);
        notifyDataSetChanged();
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MonthView view = new MonthView(parent.getContext());
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        view.setLayoutParams(params);
        view.setOnDayInMonthClickListener(CalendarAdapter.this);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        MonthEntity entity = MonthEntity.obtain(valid, select)
                .date(dates.get(position))
                .selectNote(selectNote);
        holder.view().value(entity);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
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


    private Date lastClickDate = null;
    private OnCalendarSelectListener calendarSelectListener;

    public void setOnCalendarSelectListener(OnCalendarSelectListener listener) {
        calendarSelectListener = listener;
    }

    @Override
    public void onMonthClick(Date date) {
        if (null == calendarSelectListener) {
            return;
        }
        if (null == date) {
            Log.d(TAG, "onDayInMonthClick error,receive null date");
            return;
        }
        if (null == lastClickDate) {
            lastClickDate = date;
            select(date, date);
            return;
        }
        if (lastClickDate.getTime() >= date.getTime()) {
            lastClickDate = date;
            select(date, date);
        } else {
            select(lastClickDate, date);
            Interval<Date> interval = new Interval<>();
            interval.left(lastClickDate).right(date);
            calendarSelectListener.onCalendarSelect(interval);
            Log.d(TAG, "onDayInMonthClick:" + lastClickDate.getTime() + "," + date.getTime());
            lastClickDate = null;
        }
    }
}