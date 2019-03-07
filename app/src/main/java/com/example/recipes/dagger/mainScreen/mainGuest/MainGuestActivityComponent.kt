package com.example.recipes.dagger.mainScreen.mainGuest

import com.example.recipes.dagger.activity.ActivityComponent
import com.example.recipes.mainScreen.mainGuest.MainGuestActivity
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import dagger.Component

@MainGuestActivityScope
@Component(modules = [MainGuestActivityModule::class], dependencies = [ActivityComponent::class])
interface MainGuestActivityComponent {
    fun injectMainGuestActivity(mainGuestActivity: MainGuestActivity)
}