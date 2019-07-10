package com.ctrip.framework.apollo.common.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
        "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm:ss SSS"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到系统日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatSystemDateTime() {
        return formatDateTime(new Date(System.currentTimeMillis()));
    }

    /**
     * 得到系统日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss SSS）
     */
    public static String formatSystemDateTimeMillis() {
        return formatDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss SSS");
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 获取时间的小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
     * "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     * 
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 从1970 年 1 月 1 日 00：00：00 至今的秒数
     * 
     * @return
     */
    public static String getSeconds() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 比较月份差
     * 
     * @param startDate
     * @param endDate
     * @param pattern
     * @return
     */
    public static int compareMonth(String startDate, String endDate, String pattern) {

        try {
            Date d1 = DateUtils.parseDate(startDate, pattern);
            Date d2 = DateUtils.parseDate(endDate, pattern);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(d1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(d2);

            int yearRange = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            int monthRange = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
            return (yearRange * 12 + monthRange);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断月份是否连续
     * 
     * @param months
     * @return
     */
    public static boolean isConsecutiveMonth(String[] months, String pattern) {
        boolean result = true;
        Arrays.sort(months);
        for (int i = 0; i < months.length - 1; i++) {
            int range = compareMonth(months[i], months[i + 1], pattern);
            if (range != 1) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 获取指定月份最后一天，精确到秒
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String getLastDateTimeForMonth(String date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(DateUtils.parseDate(date, pattern));
        } catch (ParseException e) {
            return null;
        }
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(ca.getTime()) + " 23:59:59";
    }

    public static int getAgeByDate(Date birthday) {
        if (birthday == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTimeInMillis() - birthday.getTime() < 0L) {
            return 0;
        }
        int yearNow = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int yearBirthday = calendar.get(Calendar.YEAR);
        return yearNow - yearBirthday;
    }

    /**
     * @Description:指定的获时间的前后日期
     * @return:
     * @Author: llznag
     * @Date: 2019/4/10 16:51
     */
    public static Date operDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.DAY_OF_MONTH, day);// 加、减
        return calendar.getTime();
    }

    public static Date stringToDate(String date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date returnDate = sdf.parse(date);
            return returnDate;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String utc2Cst(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse(dateStr);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf1.format(date);
    }

    /**
     * date2比date1多的天数 按年月日计算
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔 时分秒纳入计算 相差23.59.59 不算一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int)((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * <<<<<<< HEAD 通过时间秒毫秒数判断两个时间的间隔 相差多分
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentByMinutes(Date date1, Date date2) {
        long millisecond = date2.getTime() - date1.getTime();
        int minutes = (int)(millisecond / (1000 * 60));
        return minutes;
    }

    /**
     * @Title:
     * @Description:
     * @param time
     *            日期*
     * @param amount
     *            数量*@return*@throws*
     * 
     * @author qiaomengnan
     * @date 2019/06/25 04:43:01
     */
    public static Date addHour(Date time, Integer amount) {
        return addTime(time, Calendar.HOUR, amount);
    }

    /**
     * @Title:
     * @Description: 添加时间
     * @param time
     *            日期
     * @param field
     *            单位
     * @param amount
     *            添加数量
     * @return
     * @throws @author
     *             qiaomengnan
     * @date 2019/06/25 04:42:57
     */
    public static Date addTime(Date time, Integer field, Integer amount) {
        if (time != null && field != null && amount != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(field, amount);
            return calendar.getTime();
        }
        return null;
    }

    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * @Title:
     * @Description: 日期转字符串
     * @param date
     * @return
     * @throws @author
     *             qiaomengnan
     * @date 2019/06/28 02:12:48
     */
    public static String dateToStr(Date date, String format) {
        if (date != null && StringUtils.isNotBlank(format)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        }
        return null;
    }

    /**
     * @Title:
     * @Description: 字符串转日期
     * @param date
     * @return
     * @throws @author
     *             qiaomengnan
     * @date 2019/06/28 02:16:48
     */
    public static Date strToDate(String date, String format) {
        if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(format)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                return simpleDateFormat.parse(date);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
