package com.example.recipes.dagger.application

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {

    @Provides
    @MyApplicationScope
    @ApplicationContext
    fun applicationContext(): Application{
        return context.applicationContext as Application
    }
}