package com.example.recipes.dagger.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.recipes.mainScreen.MainActivityAdapter
import dagger.Module
import dagger.Provides

@Module
class ListsModule {

    @Provides
    @ActivityScope
    fun adapterCards(@ActivityContext context: Context): MainActivityAdapter {
        return MainActivityAdapter(context)
    }

    @Provides
    @ActivityScope
    fun linearLayoutManager(@ActivityContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }
}