package com.example.recipes.dagger.mainScreen.mainUser

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.example.recipes.dagger.activity.ActivityContext
import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.mainScreen.mainUser.MainUserContract
import com.example.recipes.mainScreen.mainUser.MainUserPresenter
import com.example.recipes.mainScreen.mainUser.MainUserViewModel
import dagger.Module
import dagger.Provides

@Module
class MainUserActivityModule(private val activity: MainUserActivity) {

/*    @Provides
    @MainUserActivityScope
    fun mainActivityView(): MainUserContract.View{
        return mainView
    }*/

    @Provides
    @MainUserActivityScope
    fun mainUserActivity(): MainUserActivity{
        return activity
    }

    @Provides
    @MainUserActivityScope
    fun viewModel(): MainUserViewModel{
        return ViewModelProviders.of(activity).get(MainUserViewModel::class.java)
    }


/*    @Provides
    @MainUserActivityScope
    fun presenter(cardsRepository: CardsRepository): MainUserContract.Presenter{
        return MainUserPresenter(mainView, cardsRepository)
    }*/
}