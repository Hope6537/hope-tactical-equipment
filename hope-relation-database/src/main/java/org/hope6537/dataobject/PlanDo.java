
package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class PlanDo extends BasicDo {

    /**
     * 作息数据 json格式
     */
    private String data;

    /**
     * 中文日期
     */
    private String day;

    /**
     * 班级ID
     */
    private Integer classesId;

    public PlanDo() {

    }

    public PlanDo(String data, String day, Integer classesId) {

        this.data = data;
        this.day = day;
        this.classesId = classesId;


    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

}
    