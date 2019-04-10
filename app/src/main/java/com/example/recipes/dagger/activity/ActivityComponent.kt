package com.example.recipes.dagger.activity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.MainCardsAdapter
import com.example.recipes.profile.FriendsAdapter
import dagger.Component

@ActivityScope
@Component(modules = [ListsModule::class, ActivityModule::class])
interface ActivityComponent {
    fun getCardsAdapter(): MainCardsAdapter
    fun getFriendsAdapter(): FriendsAdapter
    fun getLinearLayoutManager(): LinearLayoutManager
}