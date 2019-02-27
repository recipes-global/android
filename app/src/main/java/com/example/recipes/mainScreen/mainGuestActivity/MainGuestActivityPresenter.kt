package com.example.recipes.mainScreen.mainGuestActivity

import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository

class MainGuestActivityPresenter(private val mainGuestActivityView: MainGuestActivityContract.View,
                                 private val cardsRepository: CardsRepository
):
    MainGuestActivityContract.Presenter{

    private val cardsList = listOf(
        Card(1, 1, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(2, 2, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            false, 2, true, 4),
        Card(3, 3, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4)
    )


    override fun setFirstScreen() {
        mainGuestActivityView.setToolbar()
        mainGuestActivityView.setDrawerLayoutListener()
        mainGuestActivityView.setNavigationViewListener()
        mainGuestActivityView.setBottomNavigationViewListener()
        mainGuestActivityView.setSearchView()
        mainGuestActivityView.setRecyclerView(cardsList)
        mainGuestActivityView.setSwipeRefreshLayout()
    }
}