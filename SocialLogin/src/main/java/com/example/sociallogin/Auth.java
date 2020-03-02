package com.example.sociallogin;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.sociallogin.Facebook.FacebookLogin;
import com.example.sociallogin.model.UserInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Auth {
    private static Auth instance;
    private MutableLiveData<UserInfo> userInfo;
    private BaseSocialLogin socialLogin;

    private Auth() {}

    public static Auth getInstance() {
        if(instance == null) {
            instance = new Auth();
        }
        return instance;
    }

    public void login(FragmentActivity activity, @NotNull PlatformType socialType) {
        switch (socialType) {
            case Google:
                break;
            case Facebook:
                socialLogin = new FacebookLogin(activity);
                break;
            case Line:
                break;
        }

        socialLogin.login();
    }

    public void logout() {
        switch (userInfo.getValue().getPlatformType()) {
            case Google:
                break;
            case Facebook:
                socialLogin.logout(true);
                break;
            case Line:
                break;
        }

    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        socialLogin.responseListener.onResult(data.get);
        socialLogin.onActivityResult(requestCode, resultCode, data);
    }

    public MutableLiveData<UserInfo> getUserInfo() {
        if(this.userInfo == null ) {
            this.userInfo = new MutableLiveData<>();
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo.setValue(userInfo);
    }
}
