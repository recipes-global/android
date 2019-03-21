package com.example.recipes.data.repositories

import com.example.recipes.data.model.Card
import io.reactivex.Single

interface CardsRepositoryInterface {
    fun getCards(): Single<List<Card>>
}