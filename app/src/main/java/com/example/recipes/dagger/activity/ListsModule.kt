package com.example.recipes.dagger.activity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.MainCardsAdapter
import com.example.recipes.profile.FriendsAdapter
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
    fun linearLayoutManagerCards(@ActivityContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    @Provides
    @ActivityScope
    fun adapterFriends(@ActivityContext context: Context): FriendsAdapter {
        return FriendsAdapter(context)
    }
}