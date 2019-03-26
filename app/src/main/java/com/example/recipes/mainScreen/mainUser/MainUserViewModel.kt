package com.example.recipes.mainScreen.mainUser

import android.arch.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes.data.model.Card
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.login.LoginManager
import io.reactivex.disposables.Disposable

class MainUserViewModel(private val cardsRepository: CardsRepository) : ViewModel() {
    private lateinit var disposable: Disposable
    private var cardListLiveData: MutableLiveData<List<Card>> = MutableLiveData()
    private var errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
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