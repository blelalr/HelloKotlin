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

    protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    protected abstract void login();

    public void logout(boolean clearToken) {

    }

    protected void callbackAsFail(Exception exception) {
        responseListener.onResult(null, exception);
    }

    protected void callbackAsSuccess(UserInfo userInfo) {
        responseListener.onResult(userInfo, null);
    }
}
