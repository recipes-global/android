package com.example.recipes.data.repositories

import com.example.recipes.MyApplication
import com.example.recipes.data.network.RecipeAPI


class RecipeRepository {
    private var recipeAPI: RecipeAPI = MyApplication.get().getRecipeAPI()

}