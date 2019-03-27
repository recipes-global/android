package com.example.recipes.dagger.mainScreen.mainUser

import com.example.recipes.dagger.application.MyApplicationComponent
import com.example.recipes.dagger.repository.RepositoriesModule
import com.example.recipes.mainScreen.mainUser.MainUserViewModel
import dagger.Component

@ViewModelScope
@Component(modules = [RepositoriesModule::class], dependencies = [MyApplicationComponent::class])
interface MainUserViewModelComponent {
    fun injectMainUserViewModel(mainUserViewModel: MainUserViewModel)
}