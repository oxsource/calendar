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
        int[] range = new int[2];
        if (null == sDay && null == eDay) {
            //全部正常（前后开放）
            range[0] = 0;
            range[1] = maxDaysOfMonth(month) - 1;
        } else if (null == sDay && null != eDay) {
            //左区间有效（前开后闭）
            Calendar calendarMonth = calendar(month);
            Calendar calendarDay = calendar(eDay);
            if (calendarMonth.get(Calendar.YEAR) > calendarDay.get(Calendar.YEAR)) {
                //当前月年份是否在目标之后
                range[0] = -1;
                range[1] = -1;
            } else if (calendarMonth.get(Calendar.YEAR) < calendarDay.get(Calendar.YEAR)) {
                //当前月年份在目标之前
                range[0] = 0;
                range[1] = maxDaysOfMonth(month) - 1;
            } else {
                //同一年
                int days = calendarDay.get(Calendar.DAY_OF_YEAR);
                calendarMonth.set(Calendar.DAY_OF_MONTH, 1);
                int day1 = calendarMonth.get(Calendar.DAY_OF_YEAR);
                int day2 = day1 + maxDaysOfMonth(month);
                if (day1 > days) {
                    range[0] = -1;
                    range[1] = -1;
                } else {
                    range[0] = 0;
                    range[1] = days > day2 ? (day2 - day1) : (days - day1);
                }
            }
        } else if (null != sDay && null == eDay) {
            //右区间有效（前闭后开）
            Calendar calendarMonth = calendar(month);
            Calendar calendarDay = calendar(sDay);
            if (calendarMonth.get(Calendar.YEAR) > calendarDay.get(Calendar.YEAR)) {
                //当前月年份是否在目标之后
                range[0] = 0;
                range[1] = maxDaysOfMonth(month) - 1;
            } else if (calendarMonth.get(Calendar.YEAR) < calendarDay.get(Calendar.YEAR)) {
                //当前月年份在目标之前
                range[0] = -1;
                range[1] = -1;
            } else {
                //同一年
                int days = calendarDay.get(Calendar.DAY_OF_YEAR);
                calendarMonth.set(Calendar.DAY_OF_MONTH, 1);
                int day1 = calendarMonth.get(Calendar.DAY_OF_YEAR);
                int day2 = day1 + maxDaysOfMonth(month);
                if (day2 < days) {
                    range[0] = -1;
                    range[1] = -1;
                } else {
                    range[0] = days <= day1 ? 0 : (days - day1);
                    range[1] = day2 - day1;
                }
            }

        } else {
            //区间内有效（前后闭合）
        }
        return range;
    }
}
