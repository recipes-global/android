package com.example.recipes.dagger.activity

import com.example.recipes.data.repositories.CardsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    @ActivityScope
    fun cardsRepository(): CardsRepository {
        return CardsRepository()
    }
}