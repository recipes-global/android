package com.example.recipes.mainActivity

import android.content.Context
import com.example.recipes.data.model.Card

interface MainActivityContract {
    interface View{
        fun getContext(): Context?
        fun goLoginScreen()
        fun setToolbar()
        fun setRecyclerView(cardList: List<Card>?)
        fun setSwipeRefreshLayoutEnabledStatus()
        fun setDrawerLayoutListener()
        fun setNavigationViewListener()
        fun setBottomNavigationViewListener()
        fun setSearchView()
        fun setSwipeRefreshLayout()
    }

    interface Presenter{
        fun setFirstScreen()
        fun logout()
    }
}