package com.example.recipes

import android.app.Activity
import android.app.Application
import com.example.recipes.dagger.application.DaggerMyApplicationComponent
import com.example.recipes.dagger.application.MyApplicationComponent
import timber.log.Timber

class MyApplication : Application() {
    private lateinit var component: MyApplicationComponent

    companion object {
        fun get(activity: Activity): MyApplication{
            return activity.application as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component = DaggerMyApplicationComponent.builder().build()
    }

    fun getComponent(): MyApplicationComponent {
        return component
    }
}