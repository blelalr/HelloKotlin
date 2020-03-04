package com.example.sociallogin;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.sociallogin.Facebook.FacebookLogin;
import com.example.sociallogin.Google.GoogleLogin;
import com.example.sociallogin.Line.LineLogin;
import com.example.sociallogin.model.UserInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Auth {
    private static BaseSocialLogin socialLogin;
    private static MutableLiveData<UserInfo> userInfo;
    public static int GOOGLE_SIGN_IN = 1000;
    public static int LINE_SIGN_IN = 1002;
    public static String TAG = Auth.class.getSimpleName();

    public static void login(FragmentActivity activity, @NotNull PlatformType platformType) {
        switch (platformType) {
            case Google:
                socialLogin = new GoogleLogin(activity);
                break;
            case Facebook:
                socialLogin = new FacebookLogin(activity);
                break;
            case Line:
                socialLogin = new LineLogin(activity);
                break;
        }

        socialLogin.login();
    }

    public static void logout() {

        socialLogin.logout(true);
    }

    public static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        socialLogin.onActivityResult(requestCode, resultCode, data);
    }

    public static MutableLiveData<UserInfo> getUserInfo() {
        if(userInfo == null ) {
            userInfo = new MutableLiveData<>();
        }
        return userInfo;
    }

    public static void setUserInfo(UserInfo mUserInfo) {
        userInfo.setValue(mUserInfo);
    }

}
