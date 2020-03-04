
package com.example.sociallogin.model;

import com.example.sociallogin.PlatformType;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private boolean isLogin;
    private PlatformType platformType;
    private String mId;
    private String mName;
    private String mEmail;
    private String mToken;
    public UserInfo() {}

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;

    }
}
