package com.example.sociallogin.Google;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.sociallogin.Auth;
import com.example.sociallogin.BaseSocialLogin;
import com.example.sociallogin.PlatformType;
import com.example.sociallogin.R;
import com.example.sociallogin.model.UserInfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import static com.example.sociallogin.Auth.GOOGLE_SIGN_IN;

public class GoogleLogin extends BaseSocialLogin {

    private final GoogleSignInClient mGoogleSignInClient;
    private Intent signInIntent;

    public GoogleLogin(FragmentActivity activity) {
        super(activity);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken(activity.getString(R.string.google_default_web_client_id))
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
    }

    @Override
    protected void login() {
        signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d(Auth.TAG, "Success");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            UserInfo userInfo = new UserInfo();
            userInfo.setLogin(true);
            userInfo.setPlatformType(PlatformType.Google);
            userInfo.setId(account.getId());
            userInfo.setName(account.getDisplayName());
            userInfo.setToken(account.getIdToken());
            userInfo.setEmail(account.getEmail());
            // Signed in successfully, show authenticated UI.
            Auth.setUserInfo(userInfo);
            Log.d(Auth.TAG, "UserInfo" + new Gson().toJson(userInfo));


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(Auth.TAG, "Error: failed code: " + e.getStatusCode());
            Log.e(Auth.TAG, "Error: error message: " +e.getMessage());
        }
    }
}
