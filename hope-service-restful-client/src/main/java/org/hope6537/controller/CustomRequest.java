package org.hope6537.controller;

/**
 * Created by hope6537 on 16/4/16.
 */
public class CustomRequest {

    private String _mode;
    private String _auth;
    private Data data;

    public String get_mode() {
        return _mode;
    }

    public void set_mode(String _mode) {
        this._mode = _mode;
    }

    public String get_auth() {
        return _auth;
    }

    public void set_auth(String _auth) {
        this._auth = _auth;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

class Data {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}

class UserInfo {
    private String username;
    private String password;
    private String nickname;

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
}
