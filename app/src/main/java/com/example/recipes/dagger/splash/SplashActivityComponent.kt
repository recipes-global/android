package com.example.recipes.dagger.splash

import com.example.recipes.dagger.activity.ActivityComponent
import com.example.recipes.splashScreen.SplashActivity
import dagger.Component

@SplashActivityScope
@Component(modules = [SplashActivityModule::class], dependencies = [ActivityComponent::class])
interface SplashActivityComponent {
    fun injectSplashActivity(splashActivity: SplashActivity)
}