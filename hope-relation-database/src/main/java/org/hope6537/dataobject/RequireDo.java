package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class RequireDo extends BasicDo {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String des;

    /**
     * 家长ID
     */
    private Integer parentId;

    /**
     * 对应学生ID
     */
    private Integer studentId;

    /**
     * 受理教师ID
     */
    private Integer teacherId;

    /**
     * 作用日
     */
    private String date;

    /**
     * 作用时间
     */
    private String time;

    /**
     * 类型 0-每日需求 1-单日需求
     */
    private Integer type;

    public RequireDo() {

    }

    public RequireDo(String title, String des, Integer parentId, Integer studentId, Integer teacherId, String date, String time, Integer type) {

        this.title = title;
        this.des = des;
        this.parentId = parentId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.date = date;
        this.time = time;
        this.type = type;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
    