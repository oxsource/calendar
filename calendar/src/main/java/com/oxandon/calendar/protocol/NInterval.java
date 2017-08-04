package com.oxandon.calendar.protocol;

/**
 * 整数区间
 * Created by peng on 2017/8/4.
 */

public class NInterval extends Interval<Integer> {

    public NInterval() {
        left(Integer.valueOf(-1));
        lBound(-1);
        right(Integer.valueOf(-1));
        rBound(-1);
    }

    public boolean contain(int index) {
        return index >= left() && index <= right();
    }
}