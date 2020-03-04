package com.example.sociallogin.Google;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sociallogin.BaseSocialLogin;
import com.example.sociallogin.PlatformType;
import com.example.sociallogin.SocialLogin;
import com.example.sociallogin.model.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import static com.example.sociallogin.Auth.GOOGLE_SIGN_IN;
import static com.example.sociallogin.Auth.getInstance;

public class GoogleLogin extends BaseSocialLogin {

    private final GoogleSignInClient mGoogleSignInClient;
    private Intent signInIntent;

    public GoogleLogin(FragmentActivity activity) {
        super(activity);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken("789751588120-jdsvmcn0hflbav3lqq7qv9vm280vp9cu.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void logout(boolean clearToken) {
        super.logout(clearToken);
        mGoogleSignInClient.signOut();
        if(clearToken) {
            getInstance().getUserInfo().setValue(new UserInfo());
        }
    }

    @Override
    protected void login() {
        signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d(SocialLogin.TAG, "Success");

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            UserInfo userInfo = new UserInfo();
            userInfo.setLogin(true);
            userInfo.setPlatformType(PlatformType.Google);
            userInfo.setId(account.getId());
            userInfo.setName(account.getDisplayName());
            userInfo.setToken(account.getIdToken());
            userInfo.setEmail(account.getEmail());
            // Signed in successfully, show authenticated UI.
            getInstance().setUserInfo(userInfo);
            Log.d(SocialLogin.TAG, "UserInfo" + new Gson().toJson(userInfo));


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(SocialLogin.TAG, "Error: failed code: " + e.getStatusCode());
            Log.d(SocialLogin.TAG, "Error: error message: " +e.getMessage());
        }
    }
}
