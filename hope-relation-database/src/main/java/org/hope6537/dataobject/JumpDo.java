package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class JumpDo extends BasicDo {

    /**
     * 专辑ID
     */
    private Integer specialId;

    /**
     * 漫画ID
     */
    private Integer comicId;

    public JumpDo() {

    }

    public JumpDo(Integer specialId, Integer comicId) {

        this.specialId = specialId;
        this.comicId = comicId;


    }

    public Integer getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Integer specialId) {
        this.specialId = specialId;
    }

    public Integer getComicId() {
        return comicId;
    }

    public void setComicId(Integer comicId) {
        this.comicId = comicId;
    }

}
    