package com.example.hellokotlin.application

import android.app.Application
import com.example.sociallogin.SocialLogin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SocialLogin.initSocialLogin(this)
    }


}