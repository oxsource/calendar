package com.oxandon.calendar.protocol;

import android.util.Log;

import com.oxandon.calendar.annotation.DayStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * 日期单元实体信息
 * Created by peng on 2017/8/4.
 */

public class DayEntity {
    @DayStatus
    public int status;
    public int value;
    @DayStatus
    public int valueStatus;
    public String desc;
    @DayStatus
    public int descStatus;
    public String note;

    private DayEntity() {
    }

    public String value() {
        return value < 0 || value > MonthEntity.MAX_DAYS_OF_MONTH ? "" : String.valueOf(value + 1);
    }

    public String desc() {
        return null == desc ? "" : desc;
    }

    public String note() {
        return null == note ? "" : note;
    }

    public void recycle() {
        if (!pools.contains(this)) {
            this.status = DayStatus.NORMAL;
            this.value = -1;
            this.valueStatus = DayStatus.NORMAL;
            this.descStatus = DayStatus.NORMAL;
            this.desc = "";
            pools.add(this);
        }
    }

    private static final List<DayEntity> pools = new ArrayList<>();

    private static long newCount = 0;

    public static DayEntity obtain(@DayStatus int status, int value, String desc) {
        boolean empty = 0 == pools.size();
        DayEntity entity = empty ? new DayEntity() : pools.remove(0);
        if (empty) {
            newCount += 1;
        }
        Log.d("pppp", "DayEntity pool size=" + pools.size() + ",newCount= " + newCount);

        entity.status = status;
        entity.value = value;
        entity.valueStatus = status;
        entity.descStatus = status;
        entity.desc = desc;
        return entity;
    }
}