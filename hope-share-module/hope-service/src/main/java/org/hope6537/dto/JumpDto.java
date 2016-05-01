package org.hope6537.dto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class JumpDto extends BasicDto {

    /**
     * 专辑ID
     */
    private Integer specialId;

    /**
     * 漫画ID
     */
    private Integer comicId;

    /**
     * 专辑内容
     * */
    private Map<SpecialDto, List<ComicDto>> Jump = new ConcurrentHashMap<>();

    public JumpDto() {

    }

    public JumpDto(Integer specialId, Integer comicId) {

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

    public Map<SpecialDto, List<ComicDto>> getJump() {
        return Jump;
    }

    public void setJump(Map<SpecialDto, List<ComicDto>> jump) {
        Jump = jump;
    }
}

