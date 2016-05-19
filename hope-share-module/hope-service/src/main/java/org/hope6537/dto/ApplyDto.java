package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class ApplyDto extends BasicDto {

    /**
     * 教师ID
     */
    private Integer teacherId;

    /**
     * 需求ID
     */
    private Integer requiredId;

    public ApplyDto() {

    }

    public ApplyDto(Integer teacherId, Integer requiredId) {

        this.teacherId = teacherId;
        this.requiredId = requiredId;


    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getRequiredId() {
        return requiredId;
    }

    public void setRequiredId(Integer requiredId) {
        this.requiredId = requiredId;
    }

}
    