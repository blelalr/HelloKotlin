package com.example.hellokotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hellokotlin.event.EventFragment
import com.example.hellokotlin.home.HomeFragment
import com.example.hellokotlin.member.MemberFragment
import com.example.sociallogin.Auth
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        loadFragment(item.itemId)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(R.id.navigation_home)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun loadFragment(id: Int) {
        val tag = id.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (id) {
            R.id.navigation_home -> {
                HomeFragment.newInstance()
            }
            R.id.navigation_event -> {
                EventFragment.newInstance()
            }
            R.id.navigation_member -> {
                MemberFragment.newInstance()
            }
            else -> {
                null
            }
        }

        // replace fragment
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_main, fragment, tag)
                .commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Auth.onActivityResult(requestCode, resultCode, data)
    }

}
