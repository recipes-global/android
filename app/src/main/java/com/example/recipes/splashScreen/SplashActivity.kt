package com.example.recipes.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.R
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.utils.SharedPreferenceManager
import com.example.recipes.utils.UserType
import com.facebook.Profile
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    private val splashDisplayLength: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkUser()
    }

    private fun checkUser() {
        Timber.tag(TAG).d("checkUser")
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            SharedPreferenceManager.saveUserInPreferences(this, UserType.USER)
            startSplashAnimationMainLoggedScreen()

        }else{
            SharedPreferenceManager.saveUserInPreferences(this, UserType.GUEST)
            startSplashAnimationLoginScreen()
        }
    }

    private fun startSplashAnimationLoginScreen() {
        Timber.tag(TAG).d("startSplashAnimationLoginScreen")
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDisplayLength)
    }

    private fun startSplashAnimationMainLoggedScreen() {
        Timber.tag(TAG).d("startSplashAnimationMainLoggedScreen")
        Handler().postDelayed({
            val intent = Intent(this, MainUserActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDisplayLength)
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}
