package com.example.recipes.mainScreen.mainGuest

import com.example.recipes.data.repositories.CardsRepository
import io.reactivex.disposables.Disposable

class MainGuestPresenter(private val mainGuestView: MainGuestContract.View,
                         private val cardsRepository: CardsRepository):
    MainGuestContract.Presenter{
    private lateinit var disposable: Disposable

    override fun setFirstScreen() {
  //      getCardsFromServer()
        mainGuestView.setToolbar()
        mainGuestView.setNavigationViewListener()
        mainGuestView.setBottomNavigationViewListener()
        mainGuestView.setSearchView()
        mainGuestView.setSwipeRefreshLayout()
    }

/*    private fun getCardsFromServer(){
        disposable = cardsRepository.getCards().subscribe(
            { cardList -> mainGuestView.setRecyclerView(cardList) },
            { error: Throwable -> mainGuestView.showError(error.message) }
        )
    }*/

    override fun onDestroy() {
        disposable.dispose()
    }
}