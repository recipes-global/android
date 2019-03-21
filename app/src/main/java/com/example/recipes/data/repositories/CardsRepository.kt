package com.example.recipes.data.repositories

import android.app.Activity
import com.example.recipes.MyApplication
import com.example.recipes.data.model.Card
import com.example.recipes.data.network.CardAPI
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class CardsRepository(activity: Activity): CardsRepositoryInterface {
    private var cardAPI: CardAPI = MyApplication.get(activity).getComponent().getCardAPI()

    override fun getCards(): Single<List<Card>> {
        return cardAPI.getCards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}