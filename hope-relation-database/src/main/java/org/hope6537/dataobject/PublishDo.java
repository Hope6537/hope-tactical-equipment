
package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class PublishDo extends BasicDo {

    /**
     * 活动ID
     */
    private Integer eventId;

    /**
     * 班级ID
     */
    private Integer classesId;

    public PublishDo() {

    }

    public PublishDo(Integer eventId, Integer classesId) {

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
    