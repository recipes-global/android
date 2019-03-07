package com.example.recipes.mainScreen.mainUser

import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.login.LoginManager

open class MainUserActivityPresenter (private val mainActivityView: MainUserActivityContract.View,
                                                         private val cardsRepository: CardsRepository):
    MainUserActivityContract.Presenter{

    private val cardsList = listOf(
        Card(1, 1, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(2, 2, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            false, 2, true, 4),
        Card(3, 3, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4)
    )

    override fun setFirstScreen() {
        mainActivityView.setToolbar()
        mainActivityView.setListeners()
        mainActivityView.setNavigationViewListener()
        mainActivityView.setBottomNavigationViewListener()
        mainActivityView.setSearchView()
        mainActivityView.setRecyclerView(cardsList)
        mainActivityView.setSwipeRefreshLayout()
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
        mainActivityView.goLoginScreen()
    }
}