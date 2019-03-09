package com.example.recipes.dagger.profile

import com.example.recipes.dagger.activity.ActivityComponent
import com.example.recipes.profile.ProfileActivity
import dagger.Component

@ProfileActivityScope
@Component(modules = [ProfileActivityModule::class], dependencies = [ActivityComponent::class])
interface ProfileComponent {
    fun injectProfileActivity(profileActivity: ProfileActivity)
}