package com.example.recipes.mainScreen.mainGuest

import android.content.Context
import com.example.recipes.data.model.Card

interface MainGuestActivityContract {
    interface View{
        fun goLoginScreen()
        fun setToolbar()
        fun setRecyclerView(cardList: List<Card>?)
        fun setSwipeRefreshLayoutEnabledStatus()
        fun setNavigationViewListener()
        fun setBottomNavigationViewListener()
        fun setSearchView()
        fun setSwipeRefreshLayout()
        fun setFinishRefreshingSwipeRefresh()
    }

    interface Presenter{
        fun setFirstScreen()
    }
}