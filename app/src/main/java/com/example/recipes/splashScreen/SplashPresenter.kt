package com.example.recipes.splashScreen

import com.facebook.Profile

class SplashPresenter(private val splashView: SplashContract.View) : SplashContract.Presenter{

    override fun checkUser() {
        val profile = Profile.getCurrentProfile()
        if(profile != null){
            splashView.startSplashAnimation()
            splashView.goToMainActivity()
        }else{
            splashView.startSplashAnimation()
            splashView.goToLoginActivity()
        }
    }
}