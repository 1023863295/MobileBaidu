package cn.pear.mobilebaidu.tool;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by liuliang on 2017/7/11.
 */

public class DateUtils {
    public static long getDayBeginTimestamp() {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        Date dateTime = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
                * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
                * 1000);
        return dateTime.getTime();
    }

    public static long getLastMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
//将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
//将分钟至0
        c.set(Calendar.MINUTE, 0);
//将秒至0
        c.set(Calendar.SECOND,0);
//将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
// 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }

    //获取本月第一天
    public static long getThisMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
//将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
//将分钟至0
        c.set(Calendar.MINUTE, 0);
//将秒至0
        c.set(Calendar.SECOND,0);
//将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
// 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }
}
