package com.example.recipes.dagger.mainScreen.mainGuest

import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.mainGuest.MainGuestContract
import com.example.recipes.mainScreen.mainGuest.MainGuestPresenter
import dagger.Module
import dagger.Provides

@Module
class MainGuestActivityModule(private val mainView: MainGuestContract.View) {

    @Provides
    @MainGuestActivityScope
    fun mainActivityView(): MainGuestContract.View{
        return mainView
    }

    @Provides
    @MainGuestActivityScope
    fun presenter(cardsRepository: CardsRepository): MainGuestContract.Presenter{
        return MainGuestPresenter(mainView, cardsRepository)
    }
}