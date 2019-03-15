package com.example.recipes.dagger.application

import com.example.recipes.data.network.CardAPI
import com.example.recipes.data.network.RecipeAPI
import dagger.Component

@MyApplicationScope
@Component(modules = [APIModule::class])
interface MyApplicationComponent {

    fun getRecipeAPI(): RecipeAPI
    fun getCardAPI(): CardAPI

}