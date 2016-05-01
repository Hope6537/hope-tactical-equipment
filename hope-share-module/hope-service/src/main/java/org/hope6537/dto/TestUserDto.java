package org.hope6537.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class TestUserDto extends BasicDto {

    /** */
    private String username;

    /** */
    private String password;

    public TestUserDto() {

    }

    public TestUserDto(String username, String password) {

        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
    