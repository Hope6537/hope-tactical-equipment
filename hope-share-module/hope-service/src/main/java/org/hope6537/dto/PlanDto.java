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
     * 星期几
     */
    private Integer day;

    public PlanDto() {

    }

    public PlanDto(String data, Integer day) {

        this.data = data;
        this.day = day;


    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
    