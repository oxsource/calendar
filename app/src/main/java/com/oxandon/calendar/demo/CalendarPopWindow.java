package com.oxandon.calendar.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.oxandon.calendar.protocol.OnCalendarSelectListener;
import com.oxandon.calendar.utils.DateUtils;
import com.oxandon.calendar.utils.TimeUtil;
import com.oxandon.calendar.utils.ViewUtils;
import com.oxandon.calendar.view.CalendarView;

import java.util.Date;


/**
 * 日历选择PopWindow
 * Created by wangcheng on 2017/8/4.
 */

public class CalendarPopWindow extends PopupWindow implements OnCalendarSelectListener {

    private CalendarView mCalendarView;
    private LinearLayout mLlBtn;
    private Date beforeDate;
    private Date afterDate;

    public CalendarPopWindow(Context context, final OnCalendarPopSelectListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_popwindow_layout, null, false);
        ImageView cancel = (ImageView) view.findViewById(R.id.iv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button button = (Button) view.findViewById(R.id.bt_sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beforeDate != null && afterDate != null) {
                    listener.onSelect(beforeDate, afterDate);
                    dismiss();
                }
            }
        });
        mLlBtn = (LinearLayout) view.findViewById(R.id.ll_button);
        mCalendarView = (CalendarView) view.findViewById(R.id.cv_calendar);
        Date currentDate = new Date(System.currentTimeMillis());
        Date endDate = DateUtils.getLastDayFromMonth(currentDate);
        Date startDate = DateUtils.getDayYearAgo(endDate);
        mCalendarView.getAdapter().valid(null, TimeUtil.dateText(currentDate.getTime(), TimeUtil.YY_MD));
        mCalendarView.getAdapter().setOnCalendarSelectListener(this);
        mCalendarView.getAdapter().intervalNotes("开始", "结束");
        mCalendarView.show(startDate, endDate);
        DisplayMetrics metrics = ViewUtils.getDisplayMetrics(context);
        setWidth(metrics.widthPixels);
        setHeight(metrics.heightPixels - ViewUtils.getStateBarHeight(context));
        setContentView(view);
        setFocusable(true);
    }

    public void onShow(View parent) {
        if (!this.isShowing()) {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void onShow(View parent, Date before, Date after) {
        mCalendarView.getAdapter().select(before, after);
        onShow(parent);
    }

    @Override
    public void onCalendarSingleSelect(@NonNull Date date) {
        mLlBtn.setVisibility(View.GONE);
        beforeDate = null;
        afterDate = null;
    }

    @Override
    public void onCalendarBothSelect(@NonNull Date before, @NonNull Date after) {
        mLlBtn.setVisibility(View.VISIBLE);
        beforeDate = before;
        afterDate = after;
    }
}
