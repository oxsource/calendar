package com.oxandon.calendar.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by peng on 2017/8/2.
 */

public class ViewUtils {

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((px * 160) / scale + 0.5f);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dp * (scale / 160) + 0.5f);
    }

    public static int sp2px(Context context, float spVal) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, dm);
    }

    public static float px2sp(Context context, float pxVal) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (pxVal / dm.scaledDensity);
    }
}
