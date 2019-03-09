package com.example.recipes.logIn

interface LoginContract {
    interface View{
        fun setLoginButton()
        fun setNoLoginButton()
    }

    interface Presenter{
        fun setFirstScreen()
    }
}