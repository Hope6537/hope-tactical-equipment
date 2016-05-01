package org.hope6537.dataobject;

/**
 * Created by Dintama on 2016/3/12.
 */
public class UserDo extends BasicDo {

    private String username;
    private String password;
    private String nickname;
    private String sexy;
    private String email;

    public UserDo() {
    }

    public UserDo(String username, String password, String nickname, String sexy, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.sexy = sexy;
        this.email = email;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
