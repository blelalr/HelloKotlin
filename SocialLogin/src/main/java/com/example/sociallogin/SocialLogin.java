package com.example.sociallogin;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

import org.jetbrains.annotations.NotNull;

public class SocialLogin {

    public static String TAG = SocialLogin.class.getSimpleName();

    public static void initSocialLogin(@NotNull Application app) {
        for(PlatformType platformType: PlatformType.values()) {
            switch (platformType) {
                case Facebook:
                    AppEventsLogger.activateApp(app);
                    break;
            }
        }
    }


}
