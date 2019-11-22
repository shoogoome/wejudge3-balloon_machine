package net.wejudge.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

// 日期时间工具类
public class DateFormat {

    Date date = new Date();
    Map<String, Integer> EE = new HashMap<String, Integer>(){{
        put("星期一", 1);
        put("星期二", 2);
        put("星期三", 3);
        put("星期四", 4);
        put("星期五", 5);
        put("星期六", 6);
        put("星期日", 7);
    }};

    public DateFormat() {
        //        设置时区
        TimeZone time = TimeZone.getTimeZone("Asia/Chongqing");
        TimeZone.setDefault(time);
    }

    public DateFormat(long time) {
        date.setTime(time);
    }

    public void setTime(long time) {
        date.setTime(time);
    }

    public void setDate(int day) {
        int _day = this.getDate();
        date.setTime(date.getTime() + 86400000 * (day - _day));
    }

    public int getDay() {
        String time = new SimpleDateFormat("E").format(date.getTime());
        return EE.get(time);
    }

    public int getMonth() {
        String time = new SimpleDateFormat("MM").format(date.getTime());
        return Integer.parseInt(time);
    }

    public int getDate() {
        String time = new SimpleDateFormat("dd").format(date.getTime());
        return Integer.parseInt(time);
    }

    public Long getTime() {
        return date.getTime();
    }


    public static String getFormatTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static long getParseTime(String time) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime();
        } catch (Exception e) {
            System.out.println("[!] 日期转换错误: " + e);
        }
        return 0;
    }

    public static void main(String[] args) {
        DateFormat dateFormat = new DateFormat();
        System.out.println(dateFormat.getTime());
    }
}
