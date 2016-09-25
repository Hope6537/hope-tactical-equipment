package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class ApplyDo extends BasicDo {

    /**
     * 教师ID
     */
    private Integer teacherId;

    /**
     * 需求ID
     */
    private Integer requiredId;

    public ApplyDo() {

    }

    public ApplyDo(Integer teacherId, Integer requiredId) {

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
    