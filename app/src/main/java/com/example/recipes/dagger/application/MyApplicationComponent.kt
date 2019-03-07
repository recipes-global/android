package com.example.recipes.dagger.application

import com.example.recipes.data.network.RecipeAPI
import dagger.Component

@MyApplicationScope
@Component(modules = [RecipeAPIModule::class])
interface MyApplicationComponent {

    fun getRecipeAPI(): RecipeAPI

}