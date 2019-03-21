package com.example.recipes.data.repositories

import com.example.recipes.data.model.Recipe
import io.reactivex.Single

interface RecipeRepositoryInterface {
    fun getRecipe(): Single<List<Recipe>>
}