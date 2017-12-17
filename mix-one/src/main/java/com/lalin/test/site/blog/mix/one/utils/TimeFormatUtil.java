package com.lalin.test.site.blog.mix.one.utils;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @task:
 * @discrption:
 * @author: Aere
 * @date: 2017/3/28 9:29
 * @version: 1.0.0
 */
public abstract class TimeFormatUtil {

    public static final String EXTENDED_CALENDAR_DATES_TIMES = "YYYY-MM-DD hh:mm:ss";

    private static final Map<String,SimpleDateFormat> map = new ConcurrentHashMap<>();

    public static SimpleDateFormat getFormatter(String format){
        return map.computeIfAbsent(format,SimpleDateFormat::new);
    }
}
