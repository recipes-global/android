package com.example.recipes.mainScreen.mainUserActivity

import android.content.Context
import com.example.recipes.data.model.Card

interface MainUserActivityContract {
    interface View{
        fun getContext(): Context?
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
        fun logout()
    }
}