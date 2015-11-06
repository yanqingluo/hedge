package com.diorsunion.hedge.util.test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author harley-dog on 2015/7/13.
 */
public class Test {

    /**
     * 测试雪球网里返回的日期格式化
     *
     * @throws ParseException
     */
    @org.junit.Test
    public void testDateFormat() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        Date date = new Date();
        String s = date.toString();
        System.out.println(s);
        Date result = dateFormat.parse(s);
        System.out.println(result.equals(date));
    }


    /**
     * 测试随机函数
     *
     * @throws ParseException
     */
    @org.junit.Test
    public void testRandom() throws ParseException {
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++)
            System.out.println(new BigDecimal(r.nextInt(25) + 5).setScale(2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP));
    }
}
