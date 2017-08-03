package com.oxandon.calendar.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.oxandon.calendar.annotation.CalendarStatus.BOUND;
import static com.oxandon.calendar.annotation.CalendarStatus.INVALID;
import static com.oxandon.calendar.annotation.CalendarStatus.NORMAL;
import static com.oxandon.calendar.annotation.CalendarStatus.RANGE;
import static com.oxandon.calendar.annotation.CalendarStatus.STRESS;

/**
 * Created by peng on 2017/8/2.
 */

@IntDef(value = {NORMAL, INVALID, RANGE, BOUND, STRESS})
@Retention(RetentionPolicy.RUNTIME)
public @interface CalendarStatus {
    //正常
    int NORMAL = 0;
    //不可用
    int INVALID = 1;
    //范围内
    int RANGE = 2;
    //边界
    int BOUND = 3;
    //强调
    int STRESS = 4;
}
