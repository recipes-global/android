package com.example.recipes.dagger.profile

import com.example.recipes.dagger.application.MyApplicationComponent
import com.example.recipes.dagger.viewModel.ViewModelScope
import com.example.recipes.dagger.repository.RepositoriesModule
import com.example.recipes.profile.ProfileViewModel
import dagger.Component

@ViewModelScope
@Component(modules = [RepositoriesModule::class], dependencies = [MyApplicationComponent::class])
interface ProfileViewModelComponent {
    fun injectProfileViewModel(profileViewModel: ProfileViewModel)
}