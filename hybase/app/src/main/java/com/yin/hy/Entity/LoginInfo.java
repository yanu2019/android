package com.yin.hy.Entity;


import org.litepal.annotation.Column;

public class LoginInfo extends BaseEntity {
    private int id;
    private String username;
    private String password;
    private boolean autoLogin;
    private boolean isNextAutoLoginAcct;
    protected String operateTime;

    public LoginInfo() {
    }

    public LoginInfo(String username, String password, boolean autoLogin, boolean isNextAutoLoginAcct) {
        this.username = username;
        this.password = password;
        this.autoLogin = autoLogin;
        this.isNextAutoLoginAcct = isNextAutoLoginAcct;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public boolean getIsNextAutoLoginAcct() {
        return isNextAutoLoginAcct;
    }

    public void setIsNextAutoLoginAcct(boolean nextAutoLoginAcct) {
        isNextAutoLoginAcct = nextAutoLoginAcct;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", autoLigon=" + autoLogin +
                ", isNextAutoLoginAcct=" + isNextAutoLoginAcct +
                '}';
    }
}
