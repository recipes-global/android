package com.example.recipes.dagger.activity
import android.support.v7.widget.LinearLayoutManager
import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.MainActivityAdapter
import dagger.Component

@ActivityScope
@Component(modules = [ListsModule::class, ActivityModule::class, RepositoriesModule::class])
interface ActivityComponent {

    fun getAdapter(): MainActivityAdapter

    fun getLinearLayoutManager(): LinearLayoutManager

    fun getCardsRepository(): CardsRepository
}