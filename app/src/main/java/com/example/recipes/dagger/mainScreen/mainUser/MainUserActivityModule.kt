package com.example.recipes.dagger.mainScreen.mainUser

import androidx.lifecycle.ViewModelProviders
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.mainScreen.MainUserViewModel
import dagger.Module
import dagger.Provides

@Module
class MainUserActivityModule(private val activity: MainUserActivity) {
    @Provides
    @MainUserActivityScope
    fun mainUserActivity(): MainUserActivity{
        return activity
    }

    @Provides
    @MainUserActivityScope
    fun viewModel(): MainUserViewModel {
        return ViewModelProviders.of(activity).get(MainUserViewModel::class.java)
    }
}