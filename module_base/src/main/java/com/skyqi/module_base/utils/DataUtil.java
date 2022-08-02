package com.skyqi.module_base.utils;///

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/25 2:58 下午
/// * @Description: 文件说明
///
public class DataUtil {

    public static String handleDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        long day = 0;
        try {
            Date old = sdf.parse(sdf.format(date));
            Date now = sdf.parse(sdf.format(new Date()));
            long oldTime = old.getTime();
            long nowTime = now.getTime();
            day = (nowTime - oldTime) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {

        }
        if (day < 1) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(date);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd");
            return format.format(date);
        }
    }
}
