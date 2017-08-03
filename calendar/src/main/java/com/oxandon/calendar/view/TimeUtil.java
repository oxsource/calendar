package com.oxandon.calendar.view;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 时间工具
 */
public class TimeUtil {
    public final static String YY_M = "yyyy-MM";
    public final static String YY_MD = "yyyy-MM-dd";
    public final static String YY_MD_HM = "yyyy-MM-dd HH:mm";
    public final static String YY_MD_HMS = "yyyy-MM-dd HH:mm:ss";
    public final static String MD_HMS = "MM-dd HH:mm:ss";

    private final static Map<String, SimpleDateFormat> dateMap = new HashMap<>();

    private static void ensureDateFormatMap(@NonNull String format) {
        if (!dateMap.containsKey(format)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
                dateMap.put(format, sdf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Date date(String dateText, @NonNull String format) throws Exception {
        ensureDateFormatMap(format);
        if (dateMap.containsKey(format)) {
            SimpleDateFormat sdf = dateMap.get(format);
            sdf.parse(dateText);
        }
        return null;
    }

    public static String dateText(long date, @NonNull String format) {
        String value = "";
        ensureDateFormatMap(format);
        if (dateMap.containsKey(format)) {
            SimpleDateFormat sdf = dateMap.get(format);
            value = sdf.format(new Date(date));
        }
        return value;
    }

}
