package com.example.recipes.splashScreen

import android.os.Handler
import com.example.recipes.utils.SharedPreferenceManager.Companion.saveUserInPreferences
import com.example.recipes.utils.UserType
import com.facebook.Profile

class SplashPresenter(private val splashView: SplashContract.View) : SplashContract.Presenter{
    private val splashDisplayLength: Long = 1500

    override fun checkUser() {
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            saveUserInPreferences(splashView.getContext(), UserType.USER)
            startSplashAnimationMain()

        }else{
            saveUserInPreferences(splashView.getContext(), UserType.GUEST)
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