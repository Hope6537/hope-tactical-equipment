package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class JoinDto extends BasicDto {

    /**
     * 活动ID
     */
    private Integer eventId;

    /**
     * 学生ID
     */
    private Integer studentId;

    public JoinDto() {

    }

    public JoinDto(Integer eventId, Integer studentId) {

        this.eventId = eventId;
        this.studentId = studentId;


    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

}
    