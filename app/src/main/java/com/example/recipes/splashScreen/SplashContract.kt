package com.example.recipes.splashScreen

import android.content.Context

interface SplashContract {

    interface View{
        fun getContext(): Context
        fun goToLoginActivity()
        fun goToMainActivity()
    }

    interface Presenter{
        fun checkUser()
    }
}