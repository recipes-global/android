package com.example.recipes.splashScreen

import android.os.Handler
import com.facebook.Profile

class SplashPresenter(private val splashView: SplashContract.View) : SplashContract.Presenter{
    private val splashDisplayLength: Long = 1500

    override fun checkUser() {
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            startSplashAnimationMain()

        }else{
            startSplashAnimationLogin()
        }
    }

    private fun startSplashAnimationMain() {
        Handler().postDelayed({
            splashView.goToMainActivity()
        }, splashDisplayLength)
    }

    private fun startSplashAnimationLogin() {
        Handler().postDelayed({
            splashView.goToLoginActivity()
        }, splashDisplayLength)
    }
}