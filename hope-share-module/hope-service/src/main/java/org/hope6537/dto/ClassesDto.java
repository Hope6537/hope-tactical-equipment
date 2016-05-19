package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class ClassesDto extends BasicDto {

    /**
     * 名称
     */
    private String name;

    public ClassesDto() {

    }

    public ClassesDto(String name) {

        this.name = name;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
    