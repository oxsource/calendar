package com.oxandon.calendar.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.oxandon.calendar.annotation.DayStatus.BOUND;
import static com.oxandon.calendar.annotation.DayStatus.INVALID;
import static com.oxandon.calendar.annotation.DayStatus.NORMAL;
import static com.oxandon.calendar.annotation.DayStatus.RANGE;
import static com.oxandon.calendar.annotation.DayStatus.STRESS;

/**
 * Created by peng on 2017/8/2.
 */

@IntDef(value = {NORMAL, INVALID, RANGE, BOUND, STRESS})
@Retention(RetentionPolicy.RUNTIME)
public @interface DayStatus {
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
