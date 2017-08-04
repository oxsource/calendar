package com.oxandon.calendar.utils;

import com.oxandon.calendar.protocol.Interval;
import com.oxandon.calendar.protocol.NInterval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by peng on 2017/8/2.
 */

public class DateUtils {

    private static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 当月最大天数
     *
     * @param date
     * @return
     */
    public static int maxDaysOfMonth(Date date) {
        return calendar(date).getActualMaximum(Calendar.DATE);
    }

    /**
     * 当月第一天在月份表中的索引
     *
     * @param date
     * @return
     */
    public static int firstDayOfMonthIndex(Date date) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 给定日期是否是今天所在的月份
     *
     * @param date
     * @return 今天是当月几号
     */
    public static int isTodayOfMonth(Date date) {
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

    /**
     * 比较是否相同
     *
     * @param calendarA
     * @param calendarB
     * @param field
     * @return
     */
    public static boolean equals(Calendar calendarA, Calendar calendarB, int field) {
        boolean same;
        try {
            same = calendarA.get(field) == calendarB.get(field);
        } catch (Exception e) {
            same = false;
        }
        return same;
    }

    /**
     * 区间内有多少个月
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static int months(Date sDate, Date eDate) {
        Calendar before = calendar(min(sDate, eDate));
        Calendar after = calendar(max(sDate, eDate));
        int diffYear = after.get(Calendar.YEAR) - before.get(Calendar.YEAR);
        int diffMonth = after.get(Calendar.MONTH) - before.get(Calendar.MONTH);
        return diffYear * 12 + diffMonth;
    }

    public static Date max(Date sDate, Date eDate) {
        return sDate.getTime() > eDate.getTime() ? sDate : eDate;
    }

    public static Date min(Date sDate, Date eDate) {
        return sDate.getTime() > eDate.getTime() ? eDate : sDate;
    }

    /**
     * 获取区间内各月的Date
     *
     * @param sDate
     * @param eDate
     * @return
     */
    public static List<Date> fillMonths(Date sDate, Date eDate) {
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

    /**
     * 目标月份有哪些天在区间内,返回索引值区间
     * 不在范围内时返回(-1,-1)
     *
     * @param month        目标月份
     * @param dateInterval 开始,结束日期区间
     * @return 起始位置区间
     */
    public static NInterval daysInterval(Date month, Interval<Date> dateInterval) {
        final NInterval range = new NInterval();
        if (null == month || null == dateInterval) {
            return range;
        }
        final int maxDaysOfMonth = maxDaysOfMonth(month);
        Date sDay;
        Date eDay;
        //保证sDay和eDay不为空
        if (null == dateInterval.left()) {
            Calendar safeCalendar = calendar(month);
            safeCalendar.set(Calendar.DAY_OF_MONTH, 1);
            sDay = safeCalendar.getTime();
        } else {
            sDay = new Date(dateInterval.left().getTime());
        }
        if (null == dateInterval.right()) {
            Date date = max(sDay, month);
            Calendar safeCalendar = calendar(date);
            safeCalendar.set(Calendar.DAY_OF_MONTH, maxDaysOfMonth);
            eDay = safeCalendar.getTime();
        } else {
            eDay = new Date(dateInterval.right().getTime());
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
        final long dayIndex = diffDays[0] + calendars[0].get(Calendar.DAY_OF_YEAR);
        final long limitA = diffDays[1] + calendars[1].get(Calendar.DAY_OF_YEAR);
        final long limitB = diffDays[2] + calendars[2].get(Calendar.DAY_OF_YEAR);

        long temp;
        for (int i = 0; i < maxDaysOfMonth; i++) {
            temp = dayIndex + i;
            boolean contain = (temp >= limitA) && (temp <= limitB);
            if (!contain) {
                continue;
            }
            if (range.left() < 0) {
                range.left(i);
            }
            range.right(i);
            if (limitA == temp) {
                range.lBound(i);
            }
            if (limitB == temp) {
                range.rBound(i);
            }
        }
        return range;
    }

    /**
     * 根据月份及日期索引计算出指定日期
     *
     * @param month
     * @param index
     * @return
     */
    public static Date specialDayInMonth(Date month, int index) {
        Calendar calendar = calendar(month);
        calendar.set(Calendar.DAY_OF_MONTH, index + 1);
        return calendar.getTime();
    }
}