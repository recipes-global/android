package com.example.recipes.dagger.application

import com.example.recipes.MyApplication
import com.example.recipes.data.network.CardAPI
import com.example.recipes.data.network.RecipeAPI
import com.example.recipes.logIn.LoginActivity
import dagger.Component

@MyApplicationScope
@Component(modules = [APIModule::class])
interface MyApplicationComponent {
    fun injectMyApplication(myApplication: MyApplication)
}