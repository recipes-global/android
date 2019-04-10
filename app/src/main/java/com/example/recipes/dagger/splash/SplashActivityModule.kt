package com.example.recipes.dagger.splash

import androidx.lifecycle.ViewModelProviders
import com.example.recipes.splashScreen.SplashActivity
import com.example.recipes.splashScreen.SplashViewModel
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule(private val activity: SplashActivity){
    @Provides
    @SplashActivityScope
    fun splashActivity(): SplashActivity {
        return activity
    }

    @Provides
    @SplashActivityScope
    fun viewModel(): SplashViewModel {
        return ViewModelProviders.of(activity).get(SplashViewModel::class.java)
    }
}