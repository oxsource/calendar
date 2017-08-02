package com.oxandon.calendar.date;

import com.oxandon.calendar.protocol.ICalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by peng on 2017/8/2.
 */

public class DateUtils implements ICalendar {
    private final static ThreadLocal<ICalendar> threadLocal = new ThreadLocal<>();
    private static ICalendar instance;

    public static ICalendar get() {
        if (null == threadLocal.get()) {
            synchronized (DateUtils.class) {
                if (null == instance) {
                    instance = new DateUtils();
                }
                threadLocal.set(instance);
            }
        }
        return threadLocal.get();
    }

    private Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        return calendar;
    }


    public int maxDaysOfMonth(Date date) {
        return calendar(date).getActualMaximum(Calendar.DATE);
    }

    @Override
    public int firstDayOfMonthIndex(Date date) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    @Override
    public int isTodayOfMonth(Date date) {
        Calendar current = calendar(new Date());
        Calendar calendar = calendar(date);
        if (!compare(current, calendar, Calendar.YEAR)) {
            return -1;
        }
        if (!compare(current, calendar, Calendar.MONTH)) {
            return -1;
        }
        return current.get(Calendar.DAY_OF_MONTH) - 1;
    }

    @Override
    public boolean compare(Calendar calendarA, Calendar calendarB, int field) {
        boolean same;
        try {
            same = calendarA.get(field) == calendarB.get(field);
        } catch (Exception e) {
            same = false;
        }
        return same;
    }
}
