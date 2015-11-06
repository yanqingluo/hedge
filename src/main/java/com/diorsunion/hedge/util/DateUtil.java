package com.diorsunion.hedge.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author harley-dog on 2015/7/22.
 */
public class DateUtil {
    public final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    public static Date addDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
