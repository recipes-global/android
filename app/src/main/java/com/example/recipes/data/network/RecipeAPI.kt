package com.example.recipes.data.network

import com.example.recipes.data.model.Recipe
import io.reactivex.Single
import retrofit2.http.GET

interface RecipeAPI {

    @GET("/recipe")
    fun getRecipe(): Single<List<Recipe>>
}