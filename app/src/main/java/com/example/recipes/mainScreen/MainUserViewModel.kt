package com.example.recipes.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.dagger.application.DaggerMyApplicationComponent
import com.example.recipes.dagger.mainScreen.DaggerMainScreenViewModelComponent
import com.example.recipes.dagger.mainScreen.MainScreenViewModelComponent
import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.login.LoginManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class MainUserViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var cardListLiveData: MutableLiveData<List<Card>> = MutableLiveData()
    private var errorLiveData: MutableLiveData<String> = MutableLiveData()

    @Inject
    lateinit var cardsRepository: CardsRepository

    init {
        val component: MainScreenViewModelComponent = DaggerMainScreenViewModelComponent.builder()
            .myApplicationComponent(DaggerMyApplicationComponent.builder().build())
            .build()

        component.injectMainUserViewModel(this)

        getCardsFromServer()
    }

    fun getCardList(): LiveData<List<Card>>{
        return cardListLiveData
    }

    fun getError(): LiveData<String>{
        return errorLiveData
    }

    fun getCardsFromServer(){
        val disposable = cardsRepository.getCards().subscribe(
            {
            cardList -> cardListLiveData.postValue(cardList)
                        Timber.tag(TAG).d("cardList: %s", cardList.toString())
            },
            {
            error: Throwable -> errorLiveData.postValue(error.message)
                        Timber.tag(TAG).d("Error message: %s", error.message)
            }
        )
        compositeDisposable.add(disposable)
    }

    fun logout(){
        LoginManager.getInstance().logOut()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "MainUserViewModel"
    }
}