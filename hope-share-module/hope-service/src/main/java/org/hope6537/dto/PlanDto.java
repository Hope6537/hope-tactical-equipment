package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class PlanDto extends BasicDto {

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

    public PlanDto() {

    }

    public PlanDto(String data, String day, Integer classesId) {

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
    