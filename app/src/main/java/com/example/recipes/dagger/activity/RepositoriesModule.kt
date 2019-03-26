package com.example.recipes.dagger.activity

import android.app.Activity
import android.content.Context
import com.example.recipes.dagger.activity.ActivityContext
import com.example.recipes.dagger.activity.ActivityScope
import com.example.recipes.data.repositories.CardsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    @ActivityScope
    fun cardsRepository(@ActivityContext context: Context): CardsRepository {
        return CardsRepository(context as Activity)
    }
}