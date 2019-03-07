package com.example.recipes

import android.app.Activity
import android.app.Application
import com.example.recipes.dagger.application.DaggerMyApplicationComponent
import com.example.recipes.dagger.application.MyApplicationComponent
import com.example.recipes.data.network.RecipeAPI
import com.squareup.leakcanary.LeakCanary

class MyApplication : Application() {
    private lateinit var recipeAPI: RecipeAPI
    private lateinit var component: MyApplicationComponent

    companion object {
        fun get(activity: Activity): MyApplication{
            return activity.application as MyApplication
        }

        fun get(): MyApplication{
            return MyApplication()
        }
    }

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        component = DaggerMyApplicationComponent.builder().build()
        recipeAPI = component.getRecipeAPI()
    }

    fun getComponent(): MyApplicationComponent {
        return component
    }

    fun getRecipeAPI(): RecipeAPI{
        return recipeAPI
    }
}