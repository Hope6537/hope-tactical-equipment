package org.hope6537.dto;

import java.util.List;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class EventDto extends BasicDto {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String des;

    private List<ClassesDto> relationClasses;

    private Integer joinId;

    private Integer joinStatus;

    public EventDto() {

    }

    public EventDto(String title, String des) {

        this.title = title;
        this.des = des;


    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<ClassesDto> getRelationClasses() {
        return relationClasses;
    }

    public void setRelationClasses(List<ClassesDto> relationClasses) {
        this.relationClasses = relationClasses;
    }
}
    