package com.csc.cm.doorchecksystem.data.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2017/2/8.
 */
public class TimeTranUtil {

    public static String dateTimeToHmString(long timeMillis) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(timeMillis);
        return format.format(date);
    }

    public static String dateTimeToYmdString(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(timeMillis);
        return format.format(date);
    }

    public static String dateTimeToString(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(timeMillis);
        return format.format(date);
    }
}
