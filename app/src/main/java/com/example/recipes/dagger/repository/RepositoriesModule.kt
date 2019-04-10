package com.example.recipes.dagger.repository

import com.example.recipes.dagger.viewModel.ViewModelScope
import com.example.recipes.data.network.CardAPI
import com.example.recipes.data.repositories.CardsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    @ViewModelScope
    fun cardsRepository(cardAPI: CardAPI): CardsRepository {
        return CardsRepository(cardAPI)
    }
}