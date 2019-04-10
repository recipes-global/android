package com.example.recipes.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.R
import com.example.recipes.dagger.activity.ActivityModule
import com.example.recipes.dagger.activity.DaggerActivityComponent
import com.example.recipes.dagger.splash.DaggerSplashActivityComponent
import com.example.recipes.dagger.splash.SplashActivityComponent
import com.example.recipes.dagger.splash.SplashActivityModule
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.utils.SharedPreferenceManager
import com.example.recipes.utils.UserType
import com.facebook.Profile
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    private val splashDisplayLength: Long = 1500

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setDaggerComponent()
        checkUser()
    }

    private fun setDaggerComponent() {
        Timber.tag(TAG).d("setDaggerComponent")
        val activityComponent: SplashActivityComponent = DaggerSplashActivityComponent.builder()
            .splashActivityModule(SplashActivityModule(this))
            .activityComponent(
                DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this)).build())
            .build()

        activityComponent.injectSplashActivity(this)
    }

    private fun checkUser() {
        Timber.tag(TAG).d("setDaggerComponent")
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            SharedPreferenceManager.saveUserInPreferences(this, UserType.USER)
            startSplashAnimationMain()

        }else{
            SharedPreferenceManager.saveUserInPreferences(this, UserType.GUEST)
            startSplashAnimationLogin()
        }
    }

    private fun startSplashAnimationMain() {
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashDisplayLength)
    }

    private fun startSplashAnimationLogin() {
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
