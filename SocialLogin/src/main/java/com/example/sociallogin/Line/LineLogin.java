package com.example.sociallogin.Line;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sociallogin.BaseSocialLogin;
import com.example.sociallogin.PlatformType;
import com.example.sociallogin.R;
import com.example.sociallogin.SocialLogin;
import com.example.sociallogin.model.UserInfo;
import com.google.gson.Gson;
import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import java.util.Arrays;

import static com.example.sociallogin.Auth.LINE_SIGN_IN;
import static com.example.sociallogin.Auth.getInstance;

public class LineLogin extends BaseSocialLogin {

    public LineLogin(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != LINE_SIGN_IN) {
            Log.e(SocialLogin.TAG, "Unsupported Request");
            return;
        }

        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);

        switch (result.getResponseCode()) {

            case SUCCESS:
                // Login successful
                Log.d(SocialLogin.TAG, "Success");
                UserInfo userInfo = new UserInfo();
                userInfo.setLogin(true);
                userInfo.setPlatformType(PlatformType.Line);
                userInfo.setId(result.getLineProfile().getUserId());
                userInfo.setName(result.getLineProfile().getDisplayName());
                userInfo.setToken(result.getLineCredential().getAccessToken().getTokenString());
                // Signed in successfully, show authenticated UI.
                getInstance().setUserInfo(userInfo);
                Log.d(SocialLogin.TAG, "UserInfo" + new Gson().toJson(userInfo));
                break;

            case CANCEL:
                // Login canceled by user
                Log.e(SocialLogin.TAG,"ERROR: LINE Login Canceled by user.");
                break;

            default:
                // Login canceled due to other error
                Log.e(SocialLogin.TAG,"ERROR: Login FAILED!");
                Log.e(SocialLogin.TAG,"ERROR: " +result.getErrorData().toString());
        }
    }

    @Override
    protected void login() {
        try{
            Intent loginIntent = LineLoginApi.getLoginIntent(
                    activity,
                    activity.getString(R.string.line_channel_id),
                    new LineAuthenticationParams.Builder()
                            .scopes(Arrays.asList(Scope.PROFILE))
                            .build());
            activity.startActivityForResult(loginIntent, LINE_SIGN_IN);

        }
        catch(Exception e) {
            Log.e(SocialLogin.TAG,"ERROR" + e.toString());
        }
    }

    @Override
    public void logout(boolean clearToken) {
        super.logout(clearToken);
    }
}
