package com.example.recipes.dagger.profile

import androidx.lifecycle.ViewModelProviders
import com.example.recipes.dagger.mainScreen.mainUser.MainUserActivityScope
import com.example.recipes.profile.ProfileActivity
import com.example.recipes.profile.ProfileViewModel
import dagger.Module
import dagger.Provides

@Module
class ProfileActivityModule(private val activity: ProfileActivity) {
    @Provides
    @ProfileActivityScope
    fun profileActivity(): ProfileActivity {
        return activity
    }

    @Provides
    @ProfileActivityScope
    fun viewModel(): ProfileViewModel {
        return ViewModelProviders.of(activity).get(ProfileViewModel::class.java)
    }
}