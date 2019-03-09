package com.example.recipes.dagger.login

import com.example.recipes.dagger.activity.ActivityComponent
import com.example.recipes.logIn.LoginActivity
import dagger.Component

@LoginActivityScope
@Component(modules = [LoginActivityModule::class], dependencies = [ActivityComponent::class])
interface LoginActivityComponent {
    fun injectLoginActivity(loginActivity: LoginActivity)
}