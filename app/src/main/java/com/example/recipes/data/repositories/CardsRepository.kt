package com.example.recipes.data.repositories

import com.example.recipes.data.model.Card
import com.example.recipes.data.network.CardAPI
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class CardsRepository(private val cardAPI: CardAPI): CardsRepositoryInterface {

    override fun getCards(): Single<List<Card>> {
        return cardAPI.getCards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}