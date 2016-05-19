package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class RequireDto extends BasicDto {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String des;

    /**
     * 家长ID
     */
    private Integer parentId;

    /**
     * 类型 0-每日需求 1-单日需求
     */
    private Integer type;

    public RequireDto() {

    }

    public RequireDto(String title, String des, Integer parentId, Integer type) {

        this.title = title;
        this.des = des;
        this.parentId = parentId;
        this.type = type;


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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
    