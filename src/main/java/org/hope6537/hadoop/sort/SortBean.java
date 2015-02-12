package org.hope6537.hadoop.sort;

import org.apache.hadoop.io.WritableComparable;
import org.hope6537.date.DateFormatCalculate;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

/**
 * Created by Hope6537 on 2015/2/1.
 */
public class SortBean implements WritableComparable<SortBean> {

    private String username;

    private Long theIncreaseNumber;

    private Long theDecreaseNumber;

    private Long theBenefitNumber;

    private String theDate;

    public SortBean() {

    }

    public SortBean(String username, Long theIncreaseNumber, Long theDecreaseNumber, String theDate) {
        this.username = username;
        this.theIncreaseNumber = theIncreaseNumber;
        this.theDecreaseNumber = theDecreaseNumber;
        this.theBenefitNumber = this.theIncreaseNumber - this.theDecreaseNumber;
        this.theDate = theDate;
    }

    public static void setBeanData(SortBean sortBean, String username, Long theIncreaseNumber, Long theDecreaseNumber, String theDate) {
        sortBean.username = username;
        sortBean.theIncreaseNumber = theIncreaseNumber;
        sortBean.theDecreaseNumber = theDecreaseNumber;
        sortBean.theBenefitNumber = sortBean.theIncreaseNumber - sortBean.theDecreaseNumber;
        sortBean.theDate = theDate;
    }

    @Override
    public String toString() {
        return username + "\t" + getTheIncreaseNumber() + "\t" + getTheDecreaseNumber() + "\t" + getTheBenefitNumber() + "\t" + getTheDate();
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
            return (int) DateFormatCalculate.calculateTimeZone(this.getTheDate(), o.getTheDate(), DateFormatCalculate.BASIC_DATE_FORMAT, ChronoUnit.SECONDS);
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
