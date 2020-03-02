package com.hyf.backend.utils;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Elvis on 2019/12/5
 * @Email: yfelvis@gmail.com
 * @Desc: 时间工具类
 */
public class DateUtils {
    private static final int WEEK_LAST_DAY = 7;
    private static final int SATURDAY = 6;
    private static final int HOUR_LAST = 23;
    private static final int MINUTES_LAST = 59;
    private static final int SECOND_LAST = 59;
    private static final int LEAP_YEAR_DAYS = 366;
    private static final int NO_LEAP_YEAR_DAYS = 365;

    /**
     * 格式化字符串
     */
    public static class DatePattern {


        /**
         * 标准日期格式：yyyy-MM-dd
         */
        public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
        /**
         * 标准时间格式：HH:mm:ss
         */
        public static final String NORM_TIME_PATTERN = "HH:mm:ss";
        /**
         * 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm
         */
        public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
        /**
         * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
         */
        public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

        /**
         * 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
         */
        public static final String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
        /**
         * 标准日期格式：yyyy年MM月dd日
         */
        public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

        /**
         * 标准日期格式：yyyy年MM月dd日HH时mm分ss秒
         */
        public static final String CHINESE_DATETIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";

        /**
         * 标准日期格式：yyyyMMdd
         */
        public static final String PURE_DATE_PATTERN = "yyyyMMdd";

        /**
         * 标准日期格式：HHmmss
         */
        public static final String PURE_TIME_PATTERN = "HHmmss";

        /**
         * 标准日期格式：yyyyMMddHHmmss
         */
        public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

        /**
         * 标准日期格式：yyyyMMddHHmmssSSS
         */
        public static final String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";


        /**
         * HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z
         */
        public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

        /**
         * JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
         */
        public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

        /**
         * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
         */
        public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

        /**
         * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
         */
        public static final String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

        /**
         * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
         */
        public static final String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        /**
         * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
         */
        public static final String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    }

    /**
     * 国务院办公厅颁布的法定节假日
     */
    private static Map<String, List<String>> lawHolidays = new HashMap<>();

    /**
     * 由于放假需要额外工作日
     */
    private static Map<String, List<String>> extraWorkdays = new HashMap<>();


//    static {
//        File fileLawHolidays = new File("law_holidays");
//        File fileExtWorkDays = new File("ext_workdays");
//        try {
//            List<String> lawHolidaysList = FileUtils.readLines(fileLawHolidays, StandardCharsets.UTF_8);
//            List<String> extWorkDaysList = FileUtils.readLines(fileExtWorkDays, StandardCharsets.UTF_8);
//            for (String line : lawHolidaysList) {
//                String[] split = StringUtils.split(line, '=');
//                String year = split[0];
//                String lawDateStr = split[1];
//                String[] lawDateArray = StringUtils.split(lawDateStr, ',');
//                lawHolidays.put(year, Arrays.asList(lawDateArray));
//            }
//
//
//            for (String line : extWorkDaysList) {
//                String[] split = StringUtils.split(line, '=');
//                String year = split[0];
//                String extDateStr = split[1];
//                String[] extDateArray = StringUtils.split(extDateStr, ',');
//                extraWorkdays.put(year, Arrays.asList(extDateArray));
//            }
//
//        } catch (IOException e) {
//        }
//
//    }

    static Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    /**
     * 默认使用yyyy-MM-dd HH:mm:ss格式转化
     *
     * @param pattern 查看DatePattern类
     * @param date    java.util.Date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format2DateTime(Date date, String pattern) {
        return format(date, pattern);
    }

    /**
     * @param date    java.util.Date
     * @param pattern 格式
     * @return 根据pattern格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        if (Objects.isNull(date)) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (Objects.isNull(pattern)) {
            throw new IllegalArgumentException("The pattern must be not empty");
        }
        return new DateTime(date.getTime()).toString(pattern);
    }


    /**
     * @param dateStr 模式字符串
     * @param pattern 格式
     * @return Date日期类型
     */
    public static Date parse(String dateStr, String pattern) {
        if (Objects.isNull(dateStr) || "".equals(dateStr)) {
            throw new IllegalArgumentException("dateStr must be not empty");
        }
        if (Objects.isNull(pattern)) {
            throw new IllegalArgumentException("pattern must be not empty");
        }

        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(dateStr, dtf).toDate();
    }


    /**
     * 是否为同一天
     * date1: 2019/12/3 14:25:5  date2: 2019/12/3 18:04:3 返回true
     *
     * @param date1 需要比较的日期
     * @param date2 需要比较的日期
     * @return 是否是否为同一天
     */
    public static boolean isSameDay(Date date1, final Date date2) {
        boolean res = false;
        // 初始化时间
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        int intervalDays = Days.daysBetween(dt1, dt2).getDays();
        if (intervalDays == 0) {
            res = true;
        }
        return res;
    }

    /**
     * 判断是否为同一时刻
     *
     * @param date1 需要比较的日期
     * @param date2 需要比较的日期
     * @return 是否为同一时刻
     */
    public static boolean isSameTime(Date date1, final Date date2) {
        return new DateTime(date1.getTime()).equals(new DateTime(date2.getTime()));
    }

    /**
     * 判断日期是否在范围内，包含相等的日期
     * <p>
     * date:2019/12/03 15:00:00  start: 2019/12/03 16:00:00     end: 2019/12/24 16:00:00 返回true
     * <p>
     * date:2019/12/03 15:00:00  start: 2019/12/01 16:00:00     end: 2019/12/03 16:00:00 返回true
     * <p>
     * date:2019/12/03 15:00:00  start: 2019/12/01 16:00:00     end: 2019/12/05 16:00:00 返回true
     * <p>
     * date:2019/12/03 15:00:00  start: 2019/12/05 16:00:00     end: 2019/12/10 16:00:00 返回false
     *
     * @param date  判断的时期
     * @param start 开始日期
     * @param end   结束日期
     * @return true表示date在start和end的范围内
     */
    public static boolean isBetweenDate(Date date, Date start, Date end) {
        DateTime startTime = new DateTime(start.getTime()).withHourOfDay(0).
                withMinuteOfHour(0).withSecondOfMinute(0);
        DateTime endTime = new DateTime(end.getTime()).withHourOfDay(HOUR_LAST).
                withMinuteOfHour(MINUTES_LAST).withSecondOfMinute(SECOND_LAST);
        Interval interval = new Interval(startTime, endTime);
        return interval.contains(new DateTime(date.getTime()));
    }

    /**
     * 判断日期时刻是否在范围内，包含相等的日期
     * date: 2019/12/03 17:45:00 start: 2019/12/03 18:00:00   end:2019/12/24 16:0:0 返回false
     * date: 2019/12/03 17:45:00 start: 2019/12/03 10:00:00   end:2019/12/24 16:0:0 返回true
     *
     * @param date  判断的时期
     * @param start 开始的日期
     * @param end   结束的日期
     * @return true表示date在start和end的范围内
     */
    public static boolean isBetweenDateTime(Date date, Date start, Date end) {
        DateTime startTime = new DateTime(start.getTime());
        DateTime endTime = new DateTime(end.getTime());
        Interval interval = new Interval(startTime, endTime);
        return interval.contains(new DateTime(date.getTime()));
    }

    /**
     * 加月
     * date: 2019/10/03 18:00:00 month:1 返回2019/11/03 18:00:00
     *
     * @param date  给定的日期
     * @param month 月数
     * @return 返回加上的月数
     */
    public static Date addMonths(Date date, int month) {
        return new DateTime(date.getTime()).plusMonths(month).toDate();
    }

    /**
     * 加一月
     *
     * @param date 给定的日期
     * @return 返回加一月后的日期
     */
    public static Date addOneMonths(Date date) {
        return addMonths(date, 1);
    }

    /**
     * 加上给定的周数
     *
     * @param date  给定的日期
     * @param weeks 加上指定的周数
     * @return 返回加上的周数
     */
    public static Date addWeeks(Date date, int weeks) {
        return new DateTime(date.getTime()).plusWeeks(weeks).toDate();
    }

    /**
     * @param date 给定的日期
     * @return 加一周后的的日期
     */
    public static Date addOneWeeks(Date date) {
        return addWeeks(date, 1);
    }


    /**
     * @param date 给定的日期
     * @param day  天数
     * @return 加上指定天数后的日期
     */
    public static Date addDay(Date date, int day) {
        return new DateTime(date.getTime()).plusDays(day).toDate();
    }

    /**
     * @param date 给定的日期
     * @return 返回加上一天后的日期
     */
    public static Date addOneDay(Date date) {
        return addDay(date, 1);
    }

    /**
     * @param date 给定的日期
     * @param hour 小时数
     * @return 返回加上指定小时数后的日期
     */
    public static Date addHour(Date date, int hour) {
        return new DateTime(date.getTime()).plusHours(hour).toDate();
    }

    /**
     * @param date 给定的日期
     * @return 加上一个小时后的日期
     */
    public static Date addOneHour(Date date) {
        return addHour(date, 1);
    }

    /**
     * @param date    给定的日期
     * @param minutes 分钟数
     * @return 返回加上指定分钟数后的日期
     */
    public static Date addMinute(Date date, int minutes) {
        return new DateTime(date.getTime()).plusMinutes(minutes).toDate();
    }

    /**
     * @param date 给定的日期
     * @return 返回加上一分钟后的日期
     */
    public static Date addOneMinute(Date date) {
        return addMinute(date, 1);
    }

    /**
     * @param date   给定的日期
     * @param second 指定的秒数
     * @return 返回加上指定的秒数后的日期
     */
    public static Date addSecond(Date date, int second) {
        return new DateTime(date.getTime()).plusSeconds(second).toDate();
    }

    /**
     * @param date 给定的日期
     * @return 返回加上一秒后的日期
     */
    public static Date addOneSecond(Date date) {
        return addSecond(date, 1);
    }

    /**
     * 获取给定时间当前周的周一日期开始时间
     * 2019/12/3 10:13:32 转成2019/12/2 0:0:0
     *
     * @param date 给定的日期
     * @return 返回当前周周一的开始时间
     */
    public static Date getFirstDayOfWeek(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.withDayOfWeek(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
    }

    /**
     * 获取给定时间当前周的最后一天的开始时间
     * 2019/12/3 10:13:32 转成2019/12/8 0:0:0
     *
     * @param date 给定的日期
     * @return 获取给定时间当前周的最后一天的开始时间
     */
    public static Date getEndDayOfWeek(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.withDayOfWeek(WEEK_LAST_DAY).
                withHourOfDay(0).withMinuteOfHour(0).
                withSecondOfMinute(0).toDate();
    }

    /**
     * 获取给定日期是星期几
     * 2019/12/3 10:13:32 是一周中的星期2 返回2
     *
     * @param date 给定的日期
     * @return 获取给定日期是星期几
     */
    public static int getDayOfWeek(Date date) {
        return new DateTime(date.getTime()).dayOfWeek().get();
    }

    /**
     * 获得日期是一年的第几天
     * 2019/12/3 10:13:32 是一年中的第337天 返回337
     *
     * @param date 给定的日期
     * @return 获得日期是一年的第几天
     */
    public static int getDayOfYear(Date date) {
        return new DateTime(date.getTime()).dayOfYear().get();
    }

    /**
     * 获得给定日期的所在的年份开始的第一天的日期
     * 2019/12/3 10:13:32  转成 2019/1/1 0:0:0
     *
     * @param date 给定的日期
     * @return 获得给定日期的所在的年份开始的第一天的日期
     */
    public static Date beginOfYear(Date date) {
        return new DateTime(date.getTime()).withDayOfYear(1).
                withHourOfDay(0).withMinuteOfHour(0).
                withSecondOfMinute(0).toDate();
    }

    /**
     * 获得给定日期的所在年份最后一天的日期
     * 2019/12/3 10:13:32  转成 2019/12/31 23:59:59
     *
     * @param date 给定的日期
     * @return 获得给定日期的所在年份最后一天的日期
     */
    public static Date endOfYear(Date date) {
        return new DateTime(date.getTime()).plusYears(1).
                dayOfYear().withMinimumValue().withMillisOfDay(0).
                minusSeconds(1).toDate();
    }

    /**
     * 下一年开始日期
     * 2019/12/3 10:13:32  转成 2020/1/1 0:0:0
     *
     * @param date 给定的日期
     * @return 下一年开始的日期
     */
    public static Date nextYear(Date date) {
        return new DateTime(date.getTime()).plusYears(1).dayOfYear().
                withMinimumValue().withMillisOfDay(0).toDate();
    }

    /**
     * 给定日期所在的月份开始的第一天日期
     * 2019/12/3 10:13:32  转成 2019/12/1 0:0:0
     *
     * @param date 给定的日期
     * @return 给定日期所在的月份开始的第一天日期
     */
    public static Date beginOfMonth(Date date) {
        return new DateTime(date.getTime()).dayOfMonth().
                withMinimumValue().withMillisOfDay(0).toDate();
    }

    /**
     * 给定日期所在的月的最后一天日期
     * 2019/12/3 10:13:32  转成 2019/12/31 23:59:59
     *
     * @param date 给定的日期
     * @return 给定日期所在的月的最后一天日期
     */
    public static Date endOfMonth(Date date) {
        return new DateTime(date.getTime()).plusMonths(1).dayOfMonth().
                withMinimumValue().withMillisOfDay(0).minusSeconds(1).toDate();
    }

    /**
     * 下一月开始的时间
     * 2019/12/3 10:13:32  转成 2020/1/1 0:0:0
     *
     * @param date 给定的日期
     * @return 下一月开始的时间
     */
    public static Date nextMonth(Date date) {
        return new DateTime(date.getTime()).plusMonths(1).
                dayOfMonth().withMinimumValue().
                withMillisOfDay(0).toDate();
    }

    /**
     * 这一周开始的时间
     * 2019/12/3 10:13:32  转成 2019/12/2 0:0:0
     *
     * @param date 给定的日期
     * @return 这一周开始的时间
     */
    public static Date beginOfWeek(Date date) {
        return new DateTime(date.getTime()).dayOfWeek().
                withMinimumValue().withMillisOfDay(0).toDate();
    }

    /**
     * 这一周结束的时间
     * 2019/12/3 10:13:32  转成 2019/12/8 23:59:59
     *
     * @param date 给定的日期
     * @return 这一周结束的时间
     */
    public static Date endOfWeek(Date date) {
        return new DateTime(date.getTime()).dayOfWeek().
                withMaximumValue().plusDays(1).withMillisOfDay(0).
                minusSeconds(1).toDate();
    }

    /**
     * 下一周的开始时间
     * 2019/12/3 10:13:32  转成 2019/12/9 0:0:0
     *
     * @param date 给定的日期
     * @return 下一周的开始时间
     */
    public static Date nextWeek(Date date) {
        return new DateTime(date.getTime()).plusWeeks(1).
                dayOfWeek().withMinimumValue().withMillisOfDay(0).toDate();
    }

    /**
     * 获得开始的一天
     * 2019/12/3 10:13:32  转成 2019/12/3 0:0:0
     *
     * @param date 给定的日期
     * @return 获得开始的一天
     */
    public static Date beginOfDay(Date date) {
        return new DateTime(date.getTime()).withMillisOfDay(0).toDate();
    }


    /**
     * 获得一天结束的最后时分
     * 2019/12/3 10:13:32  转成 2019/12/3 23:59:59
     *
     * @param date 给定的日期
     * @return 获得一天结束的最后时分
     */
    public static Date endOfDay(Date date) {
        return new DateTime(date.getTime()).plusDays(1).withMillisOfDay(0).minusSeconds(1).toDate();
    }

    /**
     * 获得接下来一天开始时间
     * 2019/12/3 10:13:32  转成 2019/12/4 0:0:0
     *
     * @param date 给定的日期
     * @return 获得接下来一天开始时间
     */
    public static Date nextDay(Date date) {
        return new DateTime(date.getTime()).plusDays(1).withMillisOfDay(0).toDate();
    }

    /**
     * 开始的小时数
     * 2019/12/3 10:13:32  转成 2019/12/3 10:0:0
     *
     * @param date 给定的日期
     * @return 开始的小时数
     */
    public static Date beginOfHour(Date date) {
        return new DateTime(date.getTime()).hourOfDay().roundFloorCopy().toDate();
    }

    /**
     * 结束的小时数
     * 2019/12/3 10:13:32  转成 2019/12/3 10:59:59
     *
     * @param date 给定的日期
     * @return 结束的小时数
     */
    public static Date endOfHour(Date date) {
        return new DateTime(date.getTime()).hourOfDay().roundCeilingCopy().minusSeconds(1).toDate();
    }

    /**
     * 下一个小时的开始时间
     * 2019/12/3 10:13:32  转成 2019/12/3 11:0:0
     *
     * @param date 给定的日期
     * @return 下一个小时的开始时间
     */
    public static Date nextOfHour(Date date) {
        return new DateTime(date.getTime()).hourOfDay().roundCeilingCopy().toDate();
    }

    /**
     * 开始的分钟数
     * 2019/12/3 10:36:41 转成 2019/12/3 10:36:0
     *
     * @param date 给定的日期
     * @return 开始的分钟数
     */
    public static Date beginOfMinute(Date date) {
        return new DateTime(date.getTime()).minuteOfHour().roundFloorCopy().toDate();
    }

    /**
     * 结束的分钟数
     * 2019/12/3 10:36:41 转成 2019/12/3 10:36:59
     *
     * @param date 给定的日期
     * @return 结束的分钟数
     */
    public static Date endOfMinute(Date date) {
        return new DateTime(date.getTime()).minuteOfHour().roundCeilingCopy().minusSeconds(1).toDate();
    }

    /**
     * 下一个分钟数开始时间
     * 2019/12/3 10:36:41 转成 2019/12/3 10:37:0
     *
     * @param date 给定的日期
     * @return 下一个分钟数开始时间
     */
    public static Date nextMinute(Date date) {
        return new DateTime(date.getTime()).minuteOfHour().roundCeilingCopy().toDate();
    }

    /**
     * 是否是闰年
     *
     * @param date 给定的日期
     * @return true是闰年
     */
    public static boolean isLeapYear(Date date) {
        return new DateTime(date.getTime()).year().isLeap();
    }

    /**
     * 判断距离今天结束还有多久
     *
     * @return 时间戳格式
     */
    public static long getEndOfDayInterval() {
        DateTime now = DateTime.now();
        return now.millisOfDay().withMaximumValue().getMillis() - now.getMillis();
    }

    /**
     * 距离给定日期过了多少年
     *
     * @param startDate 给定的日期
     * @return 距离给定日期过了多少年
     */
    public int yearsBetween(Date startDate) {
        return Years.yearsBetween(new DateTime(startDate.getTime()), DateTime.now()).getYears();
    }

    /**
     * 距离给定日期过了多少天
     *
     * @param startDate 给定的日期
     * @return 距离给定日期过了多少天
     */
    public int daysBetween(Date startDate) {
        return Days.daysBetween(new DateTime(startDate.getTime()), DateTime.now()).getDays();
    }


    /**
     * 距离给定日期过了多少小时
     *
     * @param startDate 给定的日期
     * @return 距离给定日期过了多少小时
     */
    public int hoursBetween(Date startDate) {
        return Hours.hoursBetween(new DateTime(startDate.getTime()), DateTime.now()).getHours();
    }

    /**
     * 距离给定日期过了多少分钟
     *
     * @param startDate 给定的日期
     * @return 距离给定日期过了多少分钟
     */
    public int minutesBetween(Date startDate) {
        return Minutes.minutesBetween(new DateTime(startDate.getTime()), DateTime.now()).getMinutes();
    }

    /**
     * 距离给定日期过了多少秒
     *
     * @param startDate 给定的日期
     * @return 距离给定日期过了多少秒
     */
    public int secondsBetween(Date startDate) {
        return Seconds.secondsBetween(new DateTime(startDate.getTime()), DateTime.now()).getSeconds();
    }


    /**
     * 判断给定日期是否是节假日
     *
     * @param date 给定的日期
     * @return true 是法定节假日
     */
    public static boolean isLawHoliday(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        if (!lawHolidays.containsKey(String.valueOf(dateTime.getYear()))) {
            throw new IllegalArgumentException("输入日期非法!");
        }
        return lawHolidays.get(String.valueOf(dateTime.getYear())).
                contains(dateTime.toString(DatePattern.NORM_DATE_PATTERN));
    }

    /**
     * 判断是否是周末
     *
     * @param date 给定的日期
     * @return true 是周末
     */
    public static boolean isWeekends(Date date) {
        int dayOfWeek = new DateTime(date.getTime()).getDayOfWeek();
        return dayOfWeek == SATURDAY || dayOfWeek == WEEK_LAST_DAY;
    }

    /**
     * 判断是否是需要额外补班的周末
     *
     * @param date 给定的日期
     * @return true  是需要额外补班的周末
     */
    public static boolean isExtraWorkday(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        if (!extraWorkdays.containsKey(String.valueOf(dateTime.getYear()))) {
            throw new IllegalArgumentException("输入日期非法!");
        }
        return extraWorkdays.get(String.valueOf(dateTime.getYear())).
                contains(dateTime.toString(DatePattern.NORM_DATE_PATTERN));
    }

    /**
     * 判断是否是休息日（包含法定节假日和不需要补班的周末）
     *
     * @param date 给定的日期
     * @return true 是休息日
     */
    public static boolean isHoliday(Date date) {
        // 首先法定节假日必定是休息日
        if (isLawHoliday(date)) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!isWeekends(date)) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        return !isExtraWorkday(date);
    }

    /**
     * 判断是否是工作日
     *
     * @param date 给定的日期
     * @return true是工作日
     */
    public static boolean isWorkday(Date date) {
        return !(isHoliday(date));
    }

    /**
     * 获得一年中的总天数
     *
     * @return 获得一年中的总天数
     */
    public static int getTotalDays() {
        return new GregorianCalendar().isLeapYear(Calendar.YEAR) ? LEAP_YEAR_DAYS : NO_LEAP_YEAR_DAYS;
    }

    /**
     * @param date 给定的日期
     * @return 获得总的法定节假日天数
     */
    public static int getTotalLawHolidays(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        if (!lawHolidays.containsKey(String.valueOf(dateTime.getYear()))) {
            throw new IllegalArgumentException("输入日期非法!");
        }
        return lawHolidays.get(String.valueOf(dateTime.getYear())).size();
    }

    /**
     * @param date 给定的日期
     * @return 获得额外的需要补办的总天数
     */
    public static int getTotalExtraWorkdays(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        if (!extraWorkdays.containsKey(String.valueOf(dateTime.getYear()))) {
            throw new IllegalArgumentException("输入日期非法!");
        }
        return extraWorkdays.get(String.valueOf(dateTime.getYear())).size();
    }

    /**
     * @return 获取一年中所有周末的天数
     */
    public static int getTotalWeekends() {
        List<String> saturdays = new ArrayList<>();
        List<String> sundays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int nextYear = 1 + calendar.get(Calendar.YEAR);
        Calendar cstart = Calendar.getInstance();
        Calendar cend = Calendar.getInstance();
        cstart.set(currentYear, Calendar.JANUARY, 1);
        cend.set(nextYear, Calendar.JANUARY, 1);
        return getTotalSaturdays(saturdays, calendar, cstart, cend,
                currentYear)
                + getTotalSundays(sundays, calendar, cstart, cend,
                currentYear);
    }

    /**
     * @param saturdays   周六
     * @param calendar    Calendar对象
     * @param cstart      开始的Calendar对象
     * @param cend        结束的Calendar对象
     * @param currentYear 当前年份
     * @return 返回当前年份的所有星期6总天数
     */
    private static int getTotalSaturdays(List<String> saturdays, Calendar calendar,
                                         Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周六
        calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));
        // 从上周六往这一年的元旦开始遍历，定位到去年最后一个周六
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -WEEK_LAST_DAY);
        }
        // 将日期定位到今年第一个周六
        calendar.add(Calendar.DAY_OF_YEAR, WEEK_LAST_DAY);
        // 从本年第一个周六往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, WEEK_LAST_DAY)) {
            saturdays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return saturdays.size();
    }

    /**
     * @param sundays     周日天数集合
     * @param calendar    Calendar对象
     * @param cstart      开始的Calendar对象
     * @param cend        结束的Calendar对象
     * @param currentYear 当前年份
     * @return 返回当前年份的所有星期天总天数
     */
    private static int getTotalSundays(List<String> sundays, Calendar calendar,
                                       Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周日
        calendar.add(Calendar.DAY_OF_MONTH,
                -calendar.get(Calendar.DAY_OF_WEEK) + 1);
        // 从上周日往这一年的元旦开始遍历，定位到去年最后一个周日
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -WEEK_LAST_DAY);
        }
        // 将日期定位到今年第一个周日
        calendar.add(Calendar.DAY_OF_YEAR, WEEK_LAST_DAY);
        // 从本年第一个周日往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, WEEK_LAST_DAY)) {
            sundays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return sundays.size();
    }

    /**
     * 获得总的节假日天数
     *
     * @param date 给定的日期
     * @return 总的节假日天数
     */
    public static int getTotalHolidays(Date date) {
        //先获取不需要补班的周末天数
        int noWorkWeekends = getTotalWeekends() - getTotalExtraWorkdays(date);
        return noWorkWeekends + getTotalLawHolidays(date);
    }

    /**
     * @param calendar 使用正则表达式匹配日期格式
     */
    private static void isMatchDateFormat(String calendar) {
        Matcher matcher = pattern.matcher(calendar);
        boolean flag = matcher.matches();
        if (!flag) {
            throw new RuntimeException("输入日期格式不正确，应该为2017-02-03");
        }
    }
}
