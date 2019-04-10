package com.example.recipes.dagger.mainScreen.mainGuest

import androidx.lifecycle.ViewModelProviders
import com.example.recipes.mainScreen.mainGuest.MainGuestActivity
import com.example.recipes.mainScreen.MainUserViewModel
import dagger.Module
import dagger.Provides

@Module
class MainGuestActivityModule(private val activity: MainGuestActivity) {
    @Provides
    @MainGuestActivityScope
    fun mainGuestActivity(): MainGuestActivity {
        return activity
    }

    @Provides
    @MainGuestActivityScope
    fun viewModel(): MainUserViewModel {
        return ViewModelProviders.of(activity).get(MainUserViewModel::class.java)
    }
}