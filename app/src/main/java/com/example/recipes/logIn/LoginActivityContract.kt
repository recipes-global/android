package com.example.recipes.logIn

interface LoginActivityContract {
    interface View{
        fun setCallbackManager()
        fun setLoginButton()
        fun setNoLoginButton()
    }

    interface Presenter{
        fun setFirstScreen()
    }
}