package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class SpecialDto extends BasicDto {

    /**
     * 专辑名称
     */
    private String title;

    /**
     * 创建用户
     */
    private Integer userId;

    public SpecialDto() {

    }

    public SpecialDto(String title, Integer userId) {

        this.title = title;
        this.userId = userId;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
    