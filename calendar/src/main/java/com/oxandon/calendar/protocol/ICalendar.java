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
     * 比较是否相同
     *
     * @param calendarA
     * @param calendarB
     * @param field
     * @return
     */
    boolean equals(Calendar calendarA, Calendar calendarB, int field);

    /**
     * 区间内有多少个月
     *
     * @param sDate
     * @param eDate
     * @return
     */
    int months(Date sDate, Date eDate);

    Date max(Date sDate, Date eDate);

    Date min(Date sDate, Date eDate);

    /**
     * 获取区间内各月的Date
     *
     * @param sDate
     * @param eDate
     * @return
     */
    List<Date> fillMonths(Date sDate, Date eDate);

    /**
     * 目标月份有哪些天在区间内,返回索引值
     * b不在范围内时返回[-1,-1]
     *
     * @param month 目标月份
     * @param sDay  开始日期
     * @param eDay  结束日期
     * @return 一维数组两个元素表示起始位置
     */
    int[] containDaysIndex(Date month, Date sDay, Date eDay);
}