package com.example.recipes.dagger.mainScreen.mainUser

import com.example.recipes.dagger.activity.ActivityComponent
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import dagger.Component

@MainUserActivityScope
@Component(modules = [MainUserActivityModule::class], dependencies = [ActivityComponent::class])
interface MainUserActivityComponent {
    fun injectMainUserActivity(mainUserActivity: MainUserActivity)
}