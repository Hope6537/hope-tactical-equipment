package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class MessageDto extends BasicDto {

    /**
     * 通知ID
     */
    private Integer noticeId;

    /**
     * 班级ID
     */
    private Integer classesId;

    public MessageDto() {

    }

    public MessageDto(Integer noticeId, Integer classesId) {

        this.noticeId = noticeId;
        this.classesId = classesId;


    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

}
    