package com.example.recipes.dagger.profile

import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.profile.ProfileContract
import com.example.recipes.profile.ProfilePresenter
import dagger.Module
import dagger.Provides

@Module
class ProfileActivityModule(private val profileView: ProfileContract.View) {

    @Provides
    @ProfileActivityScope
    fun profileView(): ProfileContract.View{
        return profileView
    }

    @Provides
    @ProfileActivityScope
    fun presenter(cardsRepository: CardsRepository): ProfileContract.Presenter{
        return ProfilePresenter(profileView, cardsRepository)
    }
}