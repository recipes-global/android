package com.example.recipes.mainScreen.mainUser

import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.data.repositories.CardsRepositoryInterface
import com.facebook.login.LoginManager

open class MainUserPresenter (private val mainView: MainUserContract.View,
                              private val cardsRepository: CardsRepository):
    MainUserContract.Presenter, CardsRepositoryInterface.OnCardDisplayListener{

    private fun getCardsFromServer(){
        cardsRepository.getCards(this)
    }

    override fun setCardList(cardList: List<Card>?) {
        mainView.setRecyclerView(cardList)
    }

    override fun onError(errorMessageText: String?) {
        mainView.showError(errorMessageText)
    }

    override fun setFirstScreen() {
        getCardsFromServer()
        mainView.setToolbar()
        mainView.setListeners()
        mainView.setNavigationViewListener()
        mainView.setBottomNavigationViewListener()
        mainView.setSearchView()
        mainView.setSwipeRefreshLayout()
    }

    override fun logout() {
        LoginManager.getInstance().logOut()
        mainView.goLoginScreen()
    }
}