package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class TestComicDto extends BasicDto {

    private String title;

    private String imgTitle;

    public TestComicDto() {

    }

    public TestComicDto(String title, String imgTitle) {
        this.title = title;
        this.imgTitle = imgTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }


}
