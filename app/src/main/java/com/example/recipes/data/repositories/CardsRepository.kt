package com.example.recipes.data.repositories

import android.app.Activity
import com.example.recipes.MyApplication
import com.example.recipes.data.network.CardAPI
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class CardsRepository(activity: Activity): CardsRepositoryInterface {
    private var cardAPI: CardAPI = MyApplication.get(activity).getComponent().getCardAPI()

    override fun getCards(listener: CardsRepositoryInterface.OnCardDisplayListener) {
        val disposable = cardAPI.getCards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            {
                cardList -> listener.setCardList(cardList)
            },
            {
                error: Throwable -> listener.onError(error.message)
            }
        )

    }
}