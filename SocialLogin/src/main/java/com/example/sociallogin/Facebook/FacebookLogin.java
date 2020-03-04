package com.example.sociallogin.Facebook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sociallogin.BaseSocialLogin;
import com.example.sociallogin.PlatformType;
import com.example.sociallogin.SocialLogin;
import com.example.sociallogin.model.UserInfo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.example.sociallogin.Auth.getInstance;


public class FacebookLogin extends BaseSocialLogin {

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";


    public FacebookLogin(FragmentActivity activity) {
        super(activity);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void login() {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList(EMAIL, PUBLIC_PROFILE));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(SocialLogin.TAG, "Success");
                loginSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                Log.d(SocialLogin.TAG, "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(SocialLogin.TAG, "Error" +error.getMessage());
//                callbackAsFail(error);
            }
        });
    }

    @Override
    public void logout(boolean clearToken) {
        LoginManager.getInstance().logOut();
        if(clearToken) {
            getInstance().getUserInfo().setValue(new UserInfo());
        }
    }

    private void loginSuccess(LoginResult loginResult){
        final UserInfo userInfo = new UserInfo();
        userInfo.setToken(loginResult.getAccessToken().getToken());
        userInfo.setPlatformType(PlatformType.Facebook);
        final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject obj, GraphResponse response) {
                try {
                    if(obj != null) {
                        if(obj.getString("id") != null){
                            userInfo.setId(obj.optString("id"));
                            userInfo.setName(obj.optString("name"));
                            userInfo.setEmail(obj.optString("email"));
                            userInfo.setLogin(true);
                            getInstance().setUserInfo(userInfo);
//                            callbackAsSuccess(userInfo);
                            Log.d(SocialLogin.TAG, "UserInfo" + new Gson().toJson(userInfo));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
