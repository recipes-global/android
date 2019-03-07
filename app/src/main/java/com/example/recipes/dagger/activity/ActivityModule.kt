package com.example.recipes.dagger.activity

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val context: Activity) {

    @Provides
    @ActivityContext
    fun context(): Context {
        return context
    }
}