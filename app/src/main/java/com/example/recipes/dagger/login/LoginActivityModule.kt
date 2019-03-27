package com.example.recipes.dagger.login

import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.logIn.LoginContract
import com.example.recipes.logIn.LoginPresenter
import com.facebook.CallbackManager
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule(private val loginView: LoginContract.View) {
    @Provides
    @LoginActivityScope
    fun loginView(): LoginContract.View{
        return loginView
    }

/*    @Provides
    @LoginActivityScope
    fun presenter(cardsRepository: CardsRepository): LoginContract.Presenter{
        return LoginPresenter(loginView, cardsRepository)
    }*/

    @Provides
    @LoginActivityScope
    fun callbackManager(): CallbackManager{
        return CallbackManager.Factory.create()
    }
}