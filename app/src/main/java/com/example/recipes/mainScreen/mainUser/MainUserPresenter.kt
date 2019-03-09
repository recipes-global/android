package com.example.recipes.mainScreen.mainUser

import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.login.LoginManager

open class MainUserPresenter (private val mainView: MainUserContract.View,
                              private val cardsRepository: CardsRepository):
    MainUserContract.Presenter{

    private val cardsList = listOf(
        Card(1, 1, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(2, 2, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            false, 2, true, 4),
        Card(3, 3, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4)
    )

    override fun setFirstScreen() {
        mainView.setToolbar()
        mainView.setListeners()
        mainView.setNavigationViewListener()
        mainView.setBottomNavigationViewListener()
        mainView.setSearchView()
        mainView.setRecyclerView(cardsList)
        mainView.setSwipeRefreshLayout()
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
        mainView.goLoginScreen()
    }
}