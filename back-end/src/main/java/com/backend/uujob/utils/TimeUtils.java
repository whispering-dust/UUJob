package com.backend.uujob.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mapleleaf
 * @date 2023年04月18日16:36
 */
public class TimeUtils {
    public static String timeTransfer(Timestamp timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(timeStamp);      // 时间戳转换成时间
        return sd;
    }
}
