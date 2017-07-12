package com.kemp.tools.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wangkp on 2017/7/10.
 */

public final class TimeUtils {

    public static String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return df.format(new Date());
    }

    public static String getDateStr(String dateFormat, Date date) {
        DateFormat df = new SimpleDateFormat(dateFormat, Locale.US);
        return df.format(date);
    }

    public static String getDateStr(String dateFormat, int year, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();//把时间清到（-28800000,1970-01-01 00:00:00）
        Logger.d("=========" + calendar.getTimeInMillis() + "," + getDateStr("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        Logger.d("=========" + calendar.getTimeInMillis() + "," + getDateStr("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
        return getDateStr(dateFormat, calendar.getTime());
    }

    public static int getWeeks() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getWeeks(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        Logger.d("=========" + calendar.getTimeInMillis() + "," + getDateStr("yyyy-MM-dd HH:mm:ss", calendar.getTime()));
        return calendar.get(Calendar.WEEK_OF_YEAR);//2017年12月31日 周日是2017年的最后一周第53周，这里确返回是1。why？
    }
}
