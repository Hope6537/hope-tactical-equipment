package org.hope6537.date;

import org.hope6537.context.ApplicationConstant;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * <p>Describe: 时间格式转换—计算工具类</p>
 * <p>Using: 用于处理时间上的计算</p>
 * <p>DevelopedTime:  2013年11月2日下午05:41:13</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class DateFormatCalculate {

    public static final String BASIC_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String BASIC_DATE_FORMAT = "yyyy-MM-dd";
    public static final String BASIC_TIME_FORMAT = "HH:mm:ss";

    /**
     * <p>Describe: 提供当前时间的字符串，通过yyyy-MM-dd HH:mm:ss来定义格式</p>
     * <p>How To Work: 替换掉老版本，使用joda来格式化输出</p>
     * <p>DevelopedTime: 2015年2月12日14:27:46 </p>
     * <p>Author:Hope6537</p>
     */
    public static String createNowTime() {
        return createNowTime(BASIC_DATE_TIME_FORMAT);
    }

    public static long castToCurrentTime(String timeStr) {
        return castToCurrentTime(timeStr, BASIC_DATE_TIME_FORMAT);
    }

    public static long castToCurrentTime(String timeStr, String formatterString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
        return time.toEpochSecond(ZoneOffset.UTC);
    }

    /**
     * <p>Describe: 提供当前时间的字符串，通过formatter来定义格式</p>
     * <p>How To Work: 替换掉老版本，使用joda来格式化输出</p>
     * <p>DevelopedTime: 2015年2月12日14:27:49 </p>
     * <p>Author:Hope6537</p>
     *
     * @param formatter 格式化字符串
     */
    public static String createNowTime(String formatter) {
        DateTime dateTime = new DateTime();
        return dateTime.toString(formatter);
    }

    /**
     * <p>Describe: 提供n天之后的时间字符串，通过formatter来定义格式</p>
     * <p>How To Work: 替换掉老版本，使用joda来计算</p>
     * <p>DevelopedTime: 2015年2月12日14:30:03 </p>
     * <p>Author:Hope6537</p>
     */
    public static String createNextTimeByDays(int days, String formatter) {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(days);
        return dateTime.toString(formatter);
    }

    /**
     * <p>Describe: 计算时间间隔 输出项采用ChronoUnit对应</p>
     * <p>How To Work: 替换老版本，使用joda来计算</p>
     * <p>DevelopedTime: 2015年2月12日14:59:55 </p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime       開始日期
     * @param endTime         結束日期
     * @param formatterString 格式化字符串
     * @param type            计算格式
     */
    public static double calculateTimeZone(String startTime, String endTime, String formatterString, TemporalUnit type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);
        return start.until(end, type);
    }

    /**
     * <p>Describe: 划分时间区段,使用LocalTime版本</p>
     * <p>DevelopedTime: 2015年2月12日14:59:52 </p>
     * <p>Author:Hope6537</p>
     *
     * @param timeLine        时间区段数组
     * @param timeStr         时间字符串
     * @param formatterString 划分字符串
     * @return
     * @see
     */
    public static String timeInTimeLine(int[] timeLine, String timeStr, String formatterString) {
        if (ApplicationConstant.isNull(formatterString)) {
            formatterString = BASIC_DATE_FORMAT;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterString);
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, formatter);
        int hour = localDateTime.getHour();
        int index = 0;
        for (int i = 0; i < timeLine.length; i++) {
            if (hour > timeLine[i]) {
                index = i;
            }
        }
        return timeLine[index] + "-" + timeLine[index + 1];
    }

    /**
     * <p>Describe: 划分时间区段,使用字符串截串版本</p>
     * <p>DevelopedTime: 2015年2月12日14:59:52 </p>
     * <p>Author:Hope6537</p>
     *
     * @param timeLine 时间区段数组
     * @param timeStr  时间字符串
     * @return
     * @see
     */
    public static String timeInTimeLineSimple(String[] timeLine, String timeStr) {
        String date = timeStr.substring(0, 11);
        int hour = Integer.parseInt(timeStr.substring(11, 13));
        int index = 0;
        for (int i = 0; i < timeLine.length; i++) {
            if (hour > Integer.parseInt(timeLine[i])) {
                index = i;
            }
        }
        return date + timeLine[index] + "-" + timeLine[index + 1];
    }

    /**
     * <p>Describe: 计算时间间隔 输出格式为小时</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2013年11月2日下午05:41:12 </p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @deprecated
     */
    public static double jiSuanTimeReturnHours(String startTime, String endTime) {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double hour = (Math.abs((t2 - t1) / (1000 * 60 * 60)));
            System.out.println(hour);
            return hour;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * <p>Describe: 计算时间间隔 输出格式为小时 采用进一法 采用分钟算法</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2013-11-3下午09:06:01 </p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @deprecated
     */
    public static double jiSuanTimeReturnHoursJINYIByMinute(String startTime,
                                                            String endTime) {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double minute = (Math.abs((t2 - t1) / (1000 * 60)));
            if (minute % 60 != 0) {
                System.out.println("minute" + minute);
                System.out.println("hours" + ((int) (((int) minute) / 60) + 1));
                return (((int) minute) / 60) + 1;
            } else {
                return ((int) minute / 60);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * <p>Describe: 计算时间间隔 输出格式为小时 采用进一法 采用分钟算法</p>
     * <p>Using: </p>
     * <p>How To Work: 化为毫秒 然后计算</p>
     * <p>DevelopedTime: 2014年1月4日下午12:19:05</p>
     * <p>Author:Hope6537</p>
     *
     * @param startTime 開始日期
     * @param endTime   結束日期
     * @return double 小時
     * @deprecated
     */
    public static double jiSuanTimeReturnHoursJINYIByYear(String startTime,
                                                          String endTime) {
        System.out.println("Start" + startTime);
        System.out.println("End" + endTime);
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy/MM/dd");
        Date d1;
        Date d2;
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            double minute = (Math.abs((t2 - t1) / (1000 * 60 * 60 * 24)));
            if (minute % 60 != 0) {
                return (((int) minute) / 365) + 1;
            } else {
                return ((int) minute / 365);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @deprecated
     */
    public static double compareDateSeconds(String startTime, String endTime) {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1;
        Date d2;
        try {
            d1 = s1.parse(startTime);
            d2 = s2.parse(endTime);
            double t1 = d1.getTime();
            double t2 = d2.getTime();
            return (t1 - t2 / (1000));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Test
    public void testCastCurrentTime() {
        long time1 = castToCurrentTime("2015-02-12 01:16:46");
        long time2 = castToCurrentTime("2015-02-12 01:25:14");
        System.out.println(time2 - time1);
    }

    @Test
    public void testCreate() {
        //System.out.println(createNowTime());
        //System.out.println(calculateTimeZone(createNowTime(), createNextTimeByDays(1, BASIC_DATE_TIME_FORMAT), BASIC_DATE_TIME_FORMAT, ChronoUnit.MINUTES));
        //System.out.println(castToCurrentTime(createNowTime()));
        //System.out.println(System.currentTimeMillis());
        String timeZone = timeInTimeLineSimple(new String[]{"00", "09", "17", "21"}, "2015-02-12 07:41:56");
        String date = timeZone.substring(0, 11);
        String timeLineEnd = timeZone.substring(timeZone.lastIndexOf('-') + 1);
        System.out.println(date);
        System.out.println(timeLineEnd);
    }

}
