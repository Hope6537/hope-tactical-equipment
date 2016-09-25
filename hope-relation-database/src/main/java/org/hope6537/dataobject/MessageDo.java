package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class MessageDo extends BasicDo {

    /**
     * 通知ID
     */
    private Integer noticeId;

    /**
     * 班级ID
     */
    private Integer classesId;

    public MessageDo() {

    }

    public MessageDo(Integer noticeId, Integer classesId) {

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
    