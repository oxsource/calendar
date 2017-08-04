package com.oxandon.calendar.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by peng on 2017/8/4.
 */

public class MonthEntity {
    public static final int WEEK_DAYS = 7;
    public static final int MAX_HORIZONTAL_LINES = 6;
    public static final int MAX_DAYS_OF_MONTH = 31;
    public static final String STR_TODAY = "今天";

    public Date date;
    public Interval<Date> valid;
    public Interval<Date> select;

    private MonthEntity() {
    }

    private final static List<MonthEntity> pools = new ArrayList<>();

    public static MonthEntity obtain() {
        if (pools.size() == 0) {
            return new MonthEntity();
        } else {
            return pools.remove(0);
        }
    }

    public void recycle() {
        if (!pools.contains(this)) {
            date = null;
            valid = null;
            select = null;
            pools.add(this);
        }
    }
}