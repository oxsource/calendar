package com.oxandon.calendar.protocol;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日历计算工具接口
 * Created by peng on 2017/8/2.
 */

public interface ICalendar {
    /**
     * 当月最大天数
     *
     * @param date
     * @return
     */
    int maxDaysOfMonth(Date date);

    /**
     * 当月第一天在月份表中的索引
     *
     * @param date
     * @return
     */
    int firstDayOfMonthIndex(Date date);

    /**
     * 给定日期是否是今天所在的月份
     *
     * @param date
     * @return 今天是当月几号
     */
    int isTodayOfMonth(Date date);

    /**
     * 比较
     *
     * @param calendarA
     * @param calendarB
     * @param field
     * @return
     */
    boolean compare(Calendar calendarA, Calendar calendarB, int field);

    int months(Date sDate, Date eDate);

    Date max(Date sDate, Date eDate);

    Date min(Date sDate, Date eDate);

    List<Date> fillMonths(Date sDate, Date eDate);
}