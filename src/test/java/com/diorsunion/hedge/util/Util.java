package com.diorsunion.hedge.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author harley-dog on 15/10/24.
 */
public class Util {
    public final static DateFormat dateFormat_US = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
    public final static DateFormat dateFormat_CN = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
}
