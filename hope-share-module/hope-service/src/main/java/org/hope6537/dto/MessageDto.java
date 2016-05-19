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
     * 家长ID
     */
    private Integer parentId;

    public MessageDto() {

    }

    public MessageDto(Integer noticeId, Integer parentId) {

        this.noticeId = noticeId;
        this.parentId = parentId;


    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

}
    