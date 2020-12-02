package com.example.hellokotlin.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.R
import com.example.sociallogin.Auth
import com.example.sociallogin.PlatformType
import com.example.sociallogin.model.UserInfo
import com.google.android.material.button.MaterialButton

class MemberFragment : Fragment() {

    companion object {
        fun newInstance() = MemberFragment()
    }

    lateinit var memberViewModel: MemberViewModel
    lateinit var facebookLoginButton: MaterialButton
    lateinit var googleLoginButton: MaterialButton
    lateinit var lineLoginButton: MaterialButton
    lateinit var logoutButton: MaterialButton
    lateinit var loginBtnsRootView: LinearLayout
    lateinit var socialToken: TextView
    lateinit var userId: TextView
    lateinit var userName: TextView
    lateinit var userEmail: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.member_fragment, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        socialToken = view.findViewById(R.id.tv_token)
        userId = view.findViewById(R.id.tv_user_id)
        userName = view.findViewById(R.id.tv_name)
        userEmail = view.findViewById(R.id.tv_email)
        facebookLoginButton = view.findViewById(R.id.btn_facebook_login)
        googleLoginButton = view.findViewById(R.id.btn_google_login)
        lineLoginButton = view.findViewById(R.id.btn_line_login)
        logoutButton = view.findViewById(R.id.btn_logout)
        loginBtnsRootView = view.findViewById(R.id.login_btns_root_view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        memberViewModel = ViewModelProvider(this.requireActivity()).get(MemberViewModel::class.java)

        facebookLoginButton.setOnClickListener {
            Auth.login(activity ,PlatformType.Facebook)
        }

        googleLoginButton.setOnClickListener{
            Auth.login(activity, PlatformType.Google)
        }

        lineLoginButton.setOnClickListener{
            Auth.login(activity, PlatformType.Line)
        }

        logoutButton.setOnClickListener {
            Auth.logout()
        }

        initObserver()

    }

    private fun initObserver() {
        Auth.getUserInfo().observe(viewLifecycleOwner, Observer { userInfo:UserInfo ->

            if(userInfo.isLogin) {
                loginBtnsRootView.visibility = View.GONE
                logoutButton.visibility = View.VISIBLE
                logoutButton.text = userInfo.platformType.name + "登出"
                socialToken.text = userInfo?.token
                userId.text = userInfo?.id
                userName.text = userInfo?.name
                userEmail.text = userInfo?.getmEmail()

            } else {
                loginBtnsRootView.visibility = View.VISIBLE
                logoutButton.visibility = View.GONE
                socialToken.text = ""
                userId.text = ""
                userName.text = ""
                userEmail.text = ""
            }
//            userInfo.token?.let {
//
//
//            } ?: run {
//
//            }
        })


    }

}
