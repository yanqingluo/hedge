package com.diorsunion.hedge.util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author harley-dog on 15/10/24.
 */
public class Util {
    public final static DateFormat dateFormat_US = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
    public final static DateFormat dateFormat_CN = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Test
    public void testPrintDate() throws ParseException {
        Date date = dateFormat_CN.parse("2010-01-01");
        System.out.println(date.getTime());
    }

}
