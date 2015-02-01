package org.hope6537.hadoop.sort;

import org.apache.hadoop.io.WritableComparable;
import org.hope6537.date.DateFormatCalculate;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Hope6537 on 2015/2/1.
 */
public class SortBean implements WritableComparable<SortBean> {

    private String username;

    private Long theIncreaseNumber;

    private Long theDecreaseNumber;

    private Long theBenefitNumber;

    private String theDate;

    @Override
    public String toString() {
        return "{" +
                "用户名='" + username + '\'' +
                ", 收益" + theIncreaseNumber +
                ", 支出" + theDecreaseNumber +
                ", 毛利润" + theBenefitNumber +
                ", 记录日期'" + theDate + '\'' +
                '}';
    }

    public static void setBeanData(SortBean sortBean, String username, Long theIncreaseNumber, Long theDecreaseNumber, String theDate) {
        sortBean.username = username;
        sortBean.theIncreaseNumber = theIncreaseNumber;
        sortBean.theDecreaseNumber = theDecreaseNumber;
        sortBean.theBenefitNumber = sortBean.theIncreaseNumber - sortBean.theDecreaseNumber;
        sortBean.theDate = theDate;
    }

    public String getTheDate() {
        return theDate;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTheIncreaseNumber() {
        return theIncreaseNumber;
    }

    public void setTheIncreaseNumber(Long theIncreaseNumber) {
        this.theIncreaseNumber = theIncreaseNumber;
    }

    public Long getTheDecreaseNumber() {
        return theDecreaseNumber;
    }

    public void setTheDecreaseNumber(Long theDecreaseNumber) {
        this.theDecreaseNumber = theDecreaseNumber;
    }

    public Long getTheBenefitNumber() {
        return theBenefitNumber;
    }

    public void setTheBenefitNumber(Long theBenefitNumber) {
        this.theBenefitNumber = theBenefitNumber;
    }


    public SortBean() {

    }

    public SortBean(String username, Long theIncreaseNumber, Long theDecreaseNumber, String theDate) {
        this.username = username;
        this.theIncreaseNumber = theIncreaseNumber;
        this.theDecreaseNumber = theDecreaseNumber;
        this.theBenefitNumber = this.theIncreaseNumber - this.theDecreaseNumber;
        this.theDate = theDate;
    }

    /**
     * 这个写的不太好
     */
    @Override
    public int compareTo(SortBean o) {
        if (this.theDate.equals(o.getTheDate())) {
            if (this.theIncreaseNumber.equals(o.theIncreaseNumber)) {
                return this.theDecreaseNumber > o.theDecreaseNumber ? 1 : -1;
            } else {
                return this.theIncreaseNumber > o.theIncreaseNumber ? -1 : 1;
            }
        } else {
            return (int) DateFormatCalculate.compareDateSeconds(this.getTheDate(), o.getTheDate());
        }
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(username);
        out.writeLong(theIncreaseNumber);
        out.writeLong(theDecreaseNumber);
        out.writeLong(theBenefitNumber);
        out.writeUTF(theDate);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.username = in.readUTF();
        this.theIncreaseNumber = in.readLong();
        this.theDecreaseNumber = in.readLong();
        this.theBenefitNumber = in.readLong();
        this.theDate = in.readUTF();
    }
}
