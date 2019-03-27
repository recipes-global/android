package com.example.recipes.dagger.application

import com.example.recipes.MyApplication
import com.example.recipes.data.network.CardAPI
import dagger.Component

@MyApplicationScope
@Component(modules = [APIModule::class])
interface MyApplicationComponent {
    fun getCardApi(): CardAPI
    fun injectMyApplication(myApplication: MyApplication)
}