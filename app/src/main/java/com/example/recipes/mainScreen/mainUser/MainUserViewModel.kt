package com.example.recipes.mainScreen.mainUser

import android.arch.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.dagger.application.DaggerMyApplicationComponent
import com.example.recipes.dagger.application.MyApplicationComponent
import com.example.recipes.dagger.mainScreen.mainUser.DaggerMainUserViewModelComponent
import com.example.recipes.dagger.mainScreen.mainUser.MainUserViewModelComponent
import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.login.LoginManager
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainUserViewModel : ViewModel() {
    private lateinit var disposable: Disposable
    var cardListLiveData: MutableLiveData<List<Card>> = MutableLiveData()
    private var errorLiveData: MutableLiveData<String> = MutableLiveData()

    @Inject
    lateinit var cardsRepository: CardsRepository

    init {
        val component: MainUserViewModelComponent = DaggerMainUserViewModelComponent.builder()
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

    private fun getCardsFromServer(){
        disposable = cardsRepository.getCards().subscribe(
            {
                    cardList -> cardListLiveData.postValue(cardList)
            },
            {
                    error: Throwable -> errorLiveData.postValue(error.message)
            }
        )
    }

    fun logout(){
        LoginManager.getInstance().logOut()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}