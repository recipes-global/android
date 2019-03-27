package com.example.recipes.dagger.login

import com.example.recipes.logIn.LoginActivity
import com.facebook.CallbackManager
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule(private val activity: LoginActivity) {

    @Provides
    @LoginActivityScope
    fun callbackManager(): CallbackManager{
        return CallbackManager.Factory.create()
    }
}