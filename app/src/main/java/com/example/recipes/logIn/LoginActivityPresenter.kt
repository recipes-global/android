package com.example.recipes.logIn

import com.example.recipes.data.repositories.CardsRepository

class LoginActivityPresenter(private val loginActivityView: LoginActivityContract.View,
                             private val cardsRepository: CardsRepository):
    LoginActivityContract.Presenter{
    override fun setFirstScreen() {
        loginActivityView.setCallbackManager()
        loginActivityView.setLoginButton()
        loginActivityView.setNoLoginButton()
    }
}