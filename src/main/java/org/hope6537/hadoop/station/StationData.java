package org.hope6537.hadoop.station;

import org.apache.hadoop.io.Text;
import org.hope6537.context.ApplicationConstant;
import org.hope6537.date.DateFormatCalculate;

/**
 * Created by Hope6537 on 2015/2/12.
 */
public class StationData {

    private String imsi;

    private String location;

    private String timeLine;

    private Long time;

    public StationData(String lineData, StationDataType type, String[] timeLine) throws CastException {
        if (ApplicationConstant.isNull(timeLine)) {
            timeLine = Flag.timeLine;
        }
        String[] data = lineData.split("\t");
        try {
            if (type.equals(StationDataType.NET)) {
                this.imsi = data[0];
                this.location = data[2];
                this.time = DateFormatCalculate.castToCurrentTime(data[3]);
                this.timeLine = DateFormatCalculate.timeInTimeLineSimple(timeLine, data[3]);
            } else if (type.equals(StationDataType.POS)) {
                this.imsi = data[0];
                this.location = data[3];
                this.time = DateFormatCalculate.castToCurrentTime(data[4]);
                this.timeLine = DateFormatCalculate.timeInTimeLineSimple(timeLine, data[4]);
            } else {
                throw new CastException("没有指定类型", Flag.TYPEERROR);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CastException("数组越界", Flag.INDEXOUT);
        } catch (Exception e) {
            throw new CastException("转换构建错误", Flag.CASTERROR);
        }
    }

    public Text getKey() {
        Text result = new Text();
        result.set(imsi + "->" + timeLine);
        return result;
    }

    public Text getValue() {
        Text result = new Text();
        result.set(location + "->" + time);
        return result;
    }
}

enum StationDataType {
    POS,
    NET
}

class CastException extends Exception {
    private int flag;

    public CastException(String message, int flag) {
        super(message);
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}

class Flag {
    public static final int TYPEERROR = 0;
    public static final int CASTERROR = 1;
    public static final int INDEXOUT = 2;
    public static final String[] timeLine = {"00", "09", "17", "21"};
}