package com.example.sociallogin.Line;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sociallogin.Auth;
import com.example.sociallogin.BaseSocialLogin;
import com.example.sociallogin.PlatformType;
import com.example.sociallogin.R;
import com.example.sociallogin.model.UserInfo;
import com.google.gson.Gson;
import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import java.util.Arrays;

import static com.example.sociallogin.Auth.LINE_SIGN_IN;

public class LineLogin extends BaseSocialLogin {

    public LineLogin(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != LINE_SIGN_IN) {
            Log.e(Auth.TAG, "Unsupported Request");
            return;
        }

        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);

        switch (result.getResponseCode()) {

            case SUCCESS:
                // Login successful
                Log.d(Auth.TAG, "Success");
                UserInfo userInfo = new UserInfo();
                userInfo.setLogin(true);
                userInfo.setPlatformType(PlatformType.Line);
                userInfo.setId(result.getLineProfile().getUserId());
                userInfo.setName(result.getLineProfile().getDisplayName());
                userInfo.setToken(result.getLineCredential().getAccessToken().getTokenString());
                // Signed in successfully, show authenticated UI.
                Auth.setUserInfo(userInfo);
                Log.d(Auth.TAG, "UserInfo" + new Gson().toJson(userInfo));
                break;

            case CANCEL:
                // Login canceled by user
                Log.e(Auth.TAG,"ERROR: LINE Login Canceled by user.");
                break;

            default:
                // Login canceled due to other error
                Log.e(Auth.TAG,"ERROR: Login FAILED!");
                Log.e(Auth.TAG,"ERROR: " +result.getErrorData().toString());
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
            Log.e(Auth.TAG,"ERROR" + e.toString());
        }
    }

    @Override
    public void logout(boolean clearToken) {
        super.logout(clearToken);
    }
}
