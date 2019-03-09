package com.example.recipes.logIn

import com.example.recipes.data.repositories.CardsRepository

class LoginPresenter(private val loginView: LoginContract.View,
                     private val cardsRepository: CardsRepository):
    LoginContract.Presenter{

    override fun setFirstScreen() {
        loginView.setLoginButton()
        loginView.setNoLoginButton()
    }
}