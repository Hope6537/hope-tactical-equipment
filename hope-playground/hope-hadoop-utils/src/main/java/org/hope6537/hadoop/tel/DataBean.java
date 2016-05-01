package org.hope6537.hadoop.tel;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class DataBean implements Writable {

    private String telNum;
    private Long uploadData;
    private Long downloadData;
    private Long totalData;

    public DataBean() {

    }

    public DataBean(String telNum, String uploadData, String downloadData) {
        this.telNum = telNum;
        this.uploadData = Long.parseLong(uploadData);
        this.downloadData = Long.parseLong(downloadData);
        this.totalData = this.uploadData + this.downloadData;
    }

    public DataBean(String telNum, Long uploadData, Long downloadData) {
        this.telNum = telNum;
        this.uploadData = uploadData;
        this.downloadData = downloadData;
        this.totalData = downloadData + uploadData;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public Long getUploadData() {
        return uploadData;
    }

    public void setUploadData(Long uploadData) {
        this.uploadData = uploadData;
    }

    public Long getDownloadData() {
        return downloadData;
    }

    public void setDownloadData(Long downloadData) {
        this.downloadData = downloadData;
    }

    public Long getTotalData() {
        return totalData;
    }

    public void setTotalData(Long totalData) {
        this.totalData = totalData;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(telNum);
        out.writeUTF(uploadData.toString());
        out.writeUTF(downloadData.toString());
        out.writeUTF(totalData.toString());
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "telNum='" + telNum + '\'' +
                ", uploadData=" + uploadData +
                ", downloadData=" + downloadData +
                ", totalData=" + totalData +
                '}';
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        telNum = in.readUTF();
        uploadData = Long.parseLong(in.readUTF());
        downloadData = Long.parseLong(in.readUTF());
        totalData = Long.parseLong(in.readUTF());
    }
}
