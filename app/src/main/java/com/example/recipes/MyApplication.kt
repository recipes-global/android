package com.example.recipes

import android.app.Activity
import android.app.Application
import com.example.recipes.dagger.application.DaggerMyApplicationComponent
import com.example.recipes.dagger.application.MyApplicationComponent
import com.example.recipes.data.network.CardAPI
import com.example.recipes.data.network.RecipeAPI
import timber.log.Timber
import javax.inject.Inject

class MyApplication : Application() {
    @Inject
    lateinit var cardAPI: CardAPI

    @Inject
    lateinit var recipeAPI: RecipeAPI

    companion object {
        fun get(activity: Activity): MyApplication{
            return activity.application as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val applicationComponent: MyApplicationComponent = DaggerMyApplicationComponent.builder()
            .build()

        applicationComponent.injectMyApplication(this)
    }
}