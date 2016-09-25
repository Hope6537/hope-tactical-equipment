package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class PublishDto extends BasicDto {

    /**
     * 活动ID
     */
    private Integer eventId;

    /**
     * 班级ID
     */
    private Integer classesId;

    public PublishDto() {

    }

    public PublishDto(Integer eventId, Integer classesId) {

        this.eventId = eventId;
        this.classesId = classesId;


    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

}
    