package com.example.recipes.dagger.mainScreen

import com.example.recipes.dagger.application.MyApplicationComponent
import com.example.recipes.dagger.repository.RepositoriesModule
import com.example.recipes.dagger.viewModel.ViewModelScope
import com.example.recipes.mainScreen.MainUserViewModel
import dagger.Component

@ViewModelScope
@Component(modules = [RepositoriesModule::class], dependencies = [MyApplicationComponent::class])
interface MainScreenViewModelComponent {
    fun injectMainUserViewModel(mainUserViewModel: MainUserViewModel)
}