
package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class ClassifiedDo extends BasicDo {

    /**
     * 分类名称
     */
    private String title;

    /**
     * 分类封面
     */
    private String coverTitle;

    public ClassifiedDo() {

    }

    public ClassifiedDo(String title, String coverTitle) {

        this.title = title;
        this.coverTitle = coverTitle;


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

}
    