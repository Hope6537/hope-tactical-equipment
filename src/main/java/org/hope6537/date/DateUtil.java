package org.hope6537.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author hope6537
 */
public class DateUtil {
    /**
     * 根据传入的月份，返回上一个或后一个月份
     *
     * @param operateDate 月份
     * @param flag        -1为获得前一个月份，1为获得后一个月份
     * @return String
     * @throws java.text.ParseException
     */
    public static String getMonthAdd(String operateDate, int flag) throws ParseException {
        if (operateDate.length() == 7) {
            operateDate = operateDate + "-01";
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sdf.parse(operateDate));
        c1.add(Calendar.MONTH, flag);
        return sdf.format(c1.getTime()).substring(0, 7);
    }

    /**
     * 格式化时间，若时分秒为"00:00:00"则返回date，否则返回datetime
     *
     * @param datetime 时间String
     * @return datetime
     */
    public static String dateFormat(String datetime) {
        String date = "";
        if (datetime != null && !"null".equals(datetime.trim()) && !"".equals(datetime.trim()) && datetime.length() > 10) {
            if ("00:00:00".equals(datetime.substring(11, 19))) {
                date = datetime.substring(0, 10);
            } else {
                date = datetime.substring(0, 19);
            }
        } else {
            date = datetime;
        }
        return date;
    }
}
