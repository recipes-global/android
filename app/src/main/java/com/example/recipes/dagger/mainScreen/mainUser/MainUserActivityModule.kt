package com.example.recipes.dagger.mainScreen.mainUser

import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.mainUser.MainUserActivityContract
import com.example.recipes.mainScreen.mainUser.MainUserActivityPresenter
import dagger.Module
import dagger.Provides

@Module
class MainUserActivityModule(private val mainActivityView: MainUserActivityContract.View) {

    @Provides
    @MainUserActivityScope
    fun mainActivityView(): MainUserActivityContract.View{
        return mainActivityView
    }

    @Provides
    @MainUserActivityScope
    fun presenter(cardsRepository: CardsRepository): MainUserActivityContract.Presenter{
        return MainUserActivityPresenter(mainActivityView, cardsRepository)
    }
}