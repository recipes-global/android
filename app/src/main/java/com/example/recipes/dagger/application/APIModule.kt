package com.example.recipes.dagger.application

import com.example.recipes.data.network.CardAPI
import com.example.recipes.data.network.RecipeAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [(RetrofitModule::class)])
class APIModule {

    @Provides
    @MyApplicationScope
    fun recipeAPI(retrofit: Retrofit): RecipeAPI {
        return retrofit.create(RecipeAPI::class.java)
    }

    @Provides
    @MyApplicationScope
    fun cardAPI(retrofit: Retrofit): CardAPI {
        return retrofit.create(CardAPI::class.java)
    }
}