package com.example.recipes.mainScreen.mainUser

import com.example.recipes.data.repositories.CardsRepository
import com.facebook.login.LoginManager
import io.reactivex.disposables.Disposable

open class MainUserPresenter (private val mainView: MainUserContract.View,
                              private val cardsRepository: CardsRepository):
    MainUserContract.Presenter{
    private lateinit var disposable: Disposable

    override fun setFirstScreen() {
  //      getCardsFromServer()
        mainView.setToolbar()
        mainView.setListeners()
        mainView.setNavigationViewListener()
        mainView.setBottomNavigationViewListener()
        mainView.setSearchView()
        mainView.setSwipeRefreshLayout()
    }

/*
    private fun getCardsFromServer(){
        disposable = cardsRepository.getCards().subscribe(
            {
                    cardList -> mainView.setRecyclerView(cardList)
            },
            {
                    error: Throwable -> mainView.showError(error.message)
            }
        )
    }
*/

    override fun logout() {
        LoginManager.getInstance().logOut()
        mainView.goLoginScreen()
    }

    override fun onDestroy() {
        disposable.dispose()
    }
}