package com.example.recipes.dagger.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.recipes.MainCardsAdapter
import dagger.Module
import dagger.Provides

@Module
class ListsModule {

    @Provides
    @ActivityScope
    fun adapterCards(@ActivityContext context: Context): MainCardsAdapter {
        return MainCardsAdapter(context)
    }

    @Provides
    @ActivityScope
    fun linearLayoutManager(@ActivityContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }
}