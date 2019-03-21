package com.example.recipes.data.repositories

import android.app.Activity
import com.example.recipes.MyApplication
import com.example.recipes.data.model.Recipe
import com.example.recipes.data.network.RecipeAPI
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RecipeRepository(activity: Activity): RecipeRepositoryInterface {
    private var recipeAPI: RecipeAPI = MyApplication.get(activity).getComponent().getRecipeAPI()

    override fun getRecipe(): Single<List<Recipe>> {
        return recipeAPI.getRecipe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}