package com.example.recipes.dagger.mainScreen.mainGuest

import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.mainGuest.MainGuestActivityContract
import com.example.recipes.mainScreen.mainGuest.MainGuestActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class MainGuestActivityModule(private val mainActivityView: MainGuestActivityContract.View) {

    @Provides
    @MainGuestActivityScope
    fun mainActivityView(): MainGuestActivityContract.View{
        return mainActivityView
    }

    @Provides
    @MainGuestActivityScope
    fun presenter(cardsRepository: CardsRepository): MainGuestActivityContract.Presenter{
        return MainGuestActivityPresenter(mainActivityView, cardsRepository)
    }
}