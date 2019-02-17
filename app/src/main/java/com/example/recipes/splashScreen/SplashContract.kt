package com.example.recipes.splashScreen

interface SplashContract {

    interface View{
        fun goToLoginActivity()
        fun goToMainActivity()
    }

    interface Presenter{
        fun checkUser()
    }
}