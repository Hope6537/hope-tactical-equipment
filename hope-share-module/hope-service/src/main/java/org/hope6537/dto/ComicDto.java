package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class ComicDto extends BasicDto {

    /**
     * 漫画名称
     */
    private String title;

    /**
     * 封面存储路径
     */
    private String coverTitle;

    /**
     * 作者
     */
    private String author;

    /**
     * 漫画简介
     */
    private String introduction;

    /**
     * 漫画内容位置
     */
    private String contentTitle;

    public ComicDto() {

    }

    public ComicDto(String title, String coverTitle, String author, String introduction, String contentTitle) {

        this.title = title;
        this.coverTitle = coverTitle;
        this.author = author;
        this.introduction = introduction;
        this.contentTitle = contentTitle;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverTitle() {
        return coverTitle;
    }

    public void setCoverTitle(String coverTitle) {
        this.coverTitle = coverTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

}
    