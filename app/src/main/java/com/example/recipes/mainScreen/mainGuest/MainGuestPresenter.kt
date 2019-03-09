package com.example.recipes.mainScreen.mainGuest

import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository

class MainGuestPresenter(private val mainGuestView: MainGuestContract.View,
                         private val cardsRepository: CardsRepository):
    MainGuestContract.Presenter{

    private val cardsList = listOf(
        Card(1, 1, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(2, 2, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            false, 2, true, 4),
        Card(3, 3, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4)
    )


    override fun setFirstScreen() {
        mainGuestView.setToolbar()
        mainGuestView.setNavigationViewListener()
        mainGuestView.setBottomNavigationViewListener()
        mainGuestView.setSearchView()
        mainGuestView.setRecyclerView(cardsList)
        mainGuestView.setSwipeRefreshLayout()
    }
}