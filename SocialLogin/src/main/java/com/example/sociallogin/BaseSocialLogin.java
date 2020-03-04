package com.example.sociallogin;

import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.example.sociallogin.model.UserInfo;


public abstract class BaseSocialLogin {
    interface OnResponseListener {
        void onResult(UserInfo o, Exception exception);
    }

    OnResponseListener responseListener;

    protected FragmentActivity activity;

    public BaseSocialLogin(FragmentActivity activity) {
        this.activity = activity;
    }

    protected abstract void login();

    protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    public void logout(boolean clearToken) {
        if(clearToken) {
            Auth.setUserInfo(new UserInfo());
        }
    }

    protected void callbackAsFail(Exception exception) {
        responseListener.onResult(null, exception);
    }

    protected void callbackAsSuccess(UserInfo userInfo) {
        responseListener.onResult(userInfo, null);
    }
}
