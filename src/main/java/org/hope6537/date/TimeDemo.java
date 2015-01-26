package org.hope6537.date;

import org.apache.log4j.Logger;

public class TimeDemo {

    private Long start;
    private Long end;

    public TimeDemo() {
        start = System.currentTimeMillis();
    }

    public Double end() {
        end = System.currentTimeMillis();
        return (end - start * 1.0 / 1000);
    }

    public String endTime(String timeStap) {
        end = System.currentTimeMillis();
        Double localTime = (end - start * 1.0 / 1000);
        String localTimeStap = ChineseTimeString.SECOND;
        switch (timeStap) {
            case ChineseTimeString.DAY:
                localTime = (end - start * 1.0 / 1000 / 60 / 60 / 24);
                localTimeStap = ChineseTimeString.DAY;
                break;
            case ChineseTimeString.HOUR:
                localTime = (end - start * 1.0 / 1000 / 60 / 60);
                localTimeStap = ChineseTimeString.HOUR;
                break;
            case ChineseTimeString.MINUTE:
                localTime = (end - start * 1.0 / 1000 / 60);
                localTimeStap = ChineseTimeString.MINUTE;
                break;
            case ChineseTimeString.MONTH:
                localTime = (end - start * 1.0 / 1000 / 60 / 60 / 24 / 30);
                localTimeStap = ChineseTimeString.MONTH;
                break;
            case ChineseTimeString.MSECOND:
                localTime = (end - start * 1.0);
                localTimeStap = ChineseTimeString.MSECOND;
                break;
            case ChineseTimeString.YEAR:
                localTime = (end - start * 1.0 / 1000 / 60 / 60 / 24 / 365);
                localTimeStap = ChineseTimeString.YEAR;
                break;
            default:
                Logger.getLogger(getClass()).info("no element use second");
                break;
        }
        return localTime + localTimeStap;
    }
}