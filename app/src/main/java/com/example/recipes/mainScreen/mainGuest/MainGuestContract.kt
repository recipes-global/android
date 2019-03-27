package com.example.recipes.mainScreen.mainGuest

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.recipes.data.model.Card

interface MainGuestContract {
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
        fun showError(errorMessageText: String?)
    }

    interface Presenter{
        fun setFirstScreen()
        fun onDestroy()
    }
}