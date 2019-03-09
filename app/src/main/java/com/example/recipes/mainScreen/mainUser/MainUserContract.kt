package com.example.recipes.mainScreen.mainUser

import android.content.Context
import com.example.recipes.data.model.Card

interface MainUserContract {
    interface View{
        fun goLoginScreen()
        fun setToolbar()
        fun setListeners()
        fun setRecyclerView(cardList: List<Card>?)
        fun setSwipeRefreshLayoutEnabledStatus()
        fun setNavigationViewListener()
        fun setBottomNavigationViewListener()
        fun setSearchView()
        fun setSwipeRefreshLayout()
        fun setFinishRefreshingSwipeRefresh()
        fun setAllViewsEnabled()
        fun setAllViewsUnenabled()
    }

    interface Presenter{
        fun setFirstScreen()
        fun logout()
    }
}