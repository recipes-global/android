package com.example.recipes.splashScreen

import com.google.firebase.auth.FirebaseAuth

class SplashPresenter(private val splashView: SplashContract.View) : SplashContract.Presenter{
    private var auth = FirebaseAuth.getInstance()

    override fun checkUser() {
        if(auth.currentUser != null){
            splashView.startSplashAnimation()
            splashView.goToMainActivity()
        }else{
            splashView.startSplashAnimation()
            splashView.goToLoginActivity()
        }
    }
}