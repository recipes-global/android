package com.example.recipes.dagger.mainScreen.mainUser

import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.mainUser.MainUserContract
import com.example.recipes.mainScreen.mainUser.MainUserPresenter
import dagger.Module
import dagger.Provides

@Module
class MainUserActivityModule(private val mainView: MainUserContract.View) {

    @Provides
    @MainUserActivityScope
    fun mainActivityView(): MainUserContract.View{
        return mainView
    }

    @Provides
    @MainUserActivityScope
    fun presenter(cardsRepository: CardsRepository): MainUserContract.Presenter{
        return MainUserPresenter(mainView, cardsRepository)
    }
}