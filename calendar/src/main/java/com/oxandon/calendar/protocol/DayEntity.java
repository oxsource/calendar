package com.oxandon.calendar.protocol;

import com.oxandon.calendar.annotation.DayStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * 日期单元实体信息
 * Created by peng on 2017/8/4.
 */

public class DayEntity {
    @DayStatus
    private int status;
    private int value;
    @DayStatus
    private int valueStatus;
    private String desc;
    @DayStatus
    private int descStatus;
    private String note;

    private DayEntity() {
    }

    @DayStatus
    public int status() {
        return status;
    }

    public DayEntity status(@DayStatus int status) {
        this.status = status;
        return this;
    }

    public DayEntity value(int value) {
        this.value = value;
        return this;
    }

    public String value() {
        return value < 0 || value > MonthEntity.MAX_DAYS_OF_MONTH ? "" : String.valueOf(value + 1);
    }

    @DayStatus
    public int valueStatus() {
        return valueStatus;
    }

    public DayEntity valueStatus(@DayStatus int valueStatus) {
        this.valueStatus = valueStatus;
        return this;
    }

    public String desc() {
        return null == desc ? "" : desc;
    }


    public DayEntity desc(String desc) {
        this.desc = desc;
        return this;
    }

    @DayStatus
    public int descStatus() {
        return descStatus;
    }

    public DayEntity descStatus(@DayStatus int descStatus) {
        this.descStatus = descStatus;
        return this;
    }

    public String note() {
        return null == note ? "" : note;
    }

    public DayEntity note(String note) {
        this.note = note;
        return this;
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

    public static DayEntity obtain(@DayStatus int status, int value, String desc) {
        DayEntity entity = 0 == pools.size() ? new DayEntity() : pools.remove(0);
        entity.status = status;
        entity.value = value;
        entity.valueStatus = status;
        entity.descStatus = status;
        entity.desc = desc;
        return entity;
    }
}