package com.diorsunion.hedge.common;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author harley-dog on 2015/6/4.
 */
public final class CalendarUtils {
    /**
     * 返回某个date加上若干天后的date对象
     *
     * @return 2015-01-08 00:00:00.000
     * @params date 2015-01-05 12:13:14.234
     * @params days 3
     */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }


    /**
     * 返回当前时间加上若干天后的date对象
     *
     * @param days 3
     * @return 2015-01-05 00:00:00.000
     * @e.g current time:2015-01-05 12:13:14.234
     */
    public static Date addDays(int days) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    /**
     * 返回某个date,指定某个字段(比如月,星期)的第一天
     *
     * @param date  2015-01-05 12:13:14.234
     * @param date  2015-01-05 12:13:14.234
     * @param field Calendar.MONTH
     * @return 2015-01-01 00:00:00.000
     */
    public static Date getMondayDate(Date date, int field) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(field, 1);
        return c.getTime();
    }

    /**
     * 返回昨天
     *
     * @e.g current time:2015-01-05 12:13:14.234
     * @return 2015-01-04 00:00:00.000
     */
    public static Date getYestoday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 返回两个时间制定字段的差值
     *
     * @param begin 2015-01-05 12:13:14.234
     * @param end   2015-01-06 12:13:14.234
     * @param field Calendar.Day
     * @return 1
     */
    public static int getDiff(Date begin, Date end, int field) {
        int r = 0;
        switch (field) {
            case Calendar.MILLISECOND:
                return (int) (end.getTime() - begin.getTime());
            case Calendar.SECOND:
                return (int) ((end.getTime() - begin.getTime()) / 1000);
            case Calendar.MINUTE:
                return (int) ((end.getTime() - begin.getTime()) / 60000);
            case Calendar.HOUR:
                return (int) ((end.getTime() - begin.getTime()) / 3600000);
            case Calendar.DATE:
                return (int) ((end.getTime() - begin.getTime()) / 86400000);
            default:
                return (int) ((end.getTime() - begin.getTime()) / 86400000);
        }
    }


    public static Date addDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        Date d = getMondayDate(date, Calendar.DAY_OF_WEEK);
        System.out.println(d);

        Date yes = getYestoday();
        System.out.println(yes);

        DateFormat dateFormat_cn = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date d2000 = dateFormat_cn.parse("2000-01-01");
        System.out.println(d2000 + ":" + d2000.getTime());
    }
}
