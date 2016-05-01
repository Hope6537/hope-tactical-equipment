package org.hope6537.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class TestUserDo extends BasicDo {

    /** */
    private String username;

    /** */
    private String password;

    public TestUserDo() {

    }

    public TestUserDo(String username, String password) {

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
    