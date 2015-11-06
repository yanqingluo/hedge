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
    public static Date getBeginDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date getBeginDate(int days) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

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

    public static Date getYestoday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DATE, -1);
        return c.getTime();
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
