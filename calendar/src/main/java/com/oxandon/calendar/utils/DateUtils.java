package com.oxandon.calendar.utils;

import com.oxandon.calendar.protocol.ICalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        if (!equals(current, calendar, Calendar.YEAR)) {
            return -1;
        }
        if (!equals(current, calendar, Calendar.MONTH)) {
            return -1;
        }
        return current.get(Calendar.DAY_OF_MONTH) - 1;
    }

    @Override
    public boolean equals(Calendar calendarA, Calendar calendarB, int field) {
        boolean same;
        try {
            same = calendarA.get(field) == calendarB.get(field);
        } catch (Exception e) {
            same = false;
        }
        return same;
    }

    @Override
    public int months(Date sDate, Date eDate) {
        Calendar before = calendar(min(sDate, eDate));
        Calendar after = calendar(max(sDate, eDate));
        int diffYear = after.get(Calendar.YEAR) - before.get(Calendar.YEAR);
        int diffMonth = after.get(Calendar.MONTH) - before.get(Calendar.MONTH);
        return diffYear * 12 + diffMonth;
    }

    @Override
    public Date max(Date sDate, Date eDate) {
        return sDate.getTime() > eDate.getTime() ? sDate : eDate;
    }

    @Override
    public Date min(Date sDate, Date eDate) {
        return sDate.getTime() > eDate.getTime() ? eDate : sDate;
    }

    @Override
    public List<Date> fillMonths(Date sDate, Date eDate) {
        List<Date> dates = new ArrayList<>();
        if (null == sDate || null == eDate) {
            dates.add(new Date());
        } else {
            int months = months(sDate, eDate);
            Calendar calendar = calendar(min(sDate, eDate));
            for (int i = 0; i <= months; i++) {
                dates.add(calendar.getTime());
                int month = calendar.get(Calendar.MONTH);
                month += 1;
                calendar.set(Calendar.MONTH, month);
            }
        }
        return dates;
    }

    @Override
    public int[] containDaysIndex(Date month, Date sDay, Date eDay) {
        final int[] range = new int[]{-1, -1};
        if (null == month) {
            return range;
        }
        final int maxDaysOfMonth = maxDaysOfMonth(month);
        //保证sDay和eDay不为空
        if (null == sDay || null == eDay) {
            Calendar safeCalendar = calendar(month);
            if (null == sDay) {
                safeCalendar.set(Calendar.DAY_OF_MONTH, 1);
                sDay = safeCalendar.getTime();
            }
            if (null == eDay) {
                safeCalendar.set(Calendar.DAY_OF_MONTH, maxDaysOfMonth);
                eDay = safeCalendar.getTime();
            }
        }
        //保证日期顺序
        sDay = min(sDay, eDay);
        eDay = max(sDay, eDay);
        //以最小年份为基础
        Calendar[] calendars = new Calendar[]{calendar(month), calendar(sDay), calendar(eDay)};
        Calendar miniYearCalendar = calendars[0];
        for (int i = 1; i < calendars.length; i++) {
            if (miniYearCalendar.get(Calendar.YEAR) > calendars[i].get(Calendar.YEAR)) {
                miniYearCalendar = calendars[i];
            }
        }
        final long miniDate = miniYearCalendar.getTime().getTime();
        long[] diffDays = new long[calendars.length];
        for (int i = 0; i < calendars.length; i++) {
            Calendar cal = calendar(new Date(miniDate));
            int diffYear = calendars[i].get(Calendar.YEAR) - cal.get(Calendar.YEAR);
            for (int j = 0; j < diffYear; j++) {
                diffDays[i] += cal.getActualMaximum(Calendar.DAY_OF_YEAR);
                cal.add(Calendar.YEAR, 1);
            }
        }
        calendars[0].set(Calendar.DAY_OF_MONTH, 1);
        long dayIndex = diffDays[0] + calendars[0].get(Calendar.DAY_OF_YEAR);
        long limitA = diffDays[1] + calendars[1].get(Calendar.DAY_OF_YEAR);
        long limitB = diffDays[2] + calendars[2].get(Calendar.DAY_OF_YEAR);

        long temp;
        for (int i = 0; i < maxDaysOfMonth; i++) {
            temp = dayIndex + i;
            boolean contain = (temp >= limitA) && (temp <= limitB);
            if (range[0] < 0 && contain) {
                range[0] = i;
            } else if (range[0] >= 0 && contain) {
                range[1] = i;
            }
        }
        return range;
    }
}