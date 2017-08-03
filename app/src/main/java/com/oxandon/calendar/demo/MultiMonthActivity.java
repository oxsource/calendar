package com.oxandon.calendar.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.oxandon.calendar.annotation.CalendarStatus;
import com.oxandon.calendar.view.MonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by peng on 2017/8/2.
 */

public class MultiMonthActivity extends Activity {
    private RecyclerView recyclerView;
    private CalendarAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_month);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new CalendarAdapter();
        recyclerView.setAdapter(adapter);
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        private final MonthView view;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            view = (MonthView) itemView;
        }

        public MonthView view() {
            return view;
        }
    }

    static class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
        private final List<Date> list = new ArrayList<>();

        public CalendarAdapter() {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            Date date = new Date();
            calendar.setTime(date);
            for (int i = 0; i < 50; i++) {
                list.add(calendar.getTime());
                int month = calendar.get(Calendar.MONTH);
                month -= 1;
                calendar.set(Calendar.MONTH, month);
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
            Date date = list.get(position);
            holder.view().value(date, CalendarStatus.NORMAL);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
