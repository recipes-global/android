package com.example.recipes.data.repositories

import com.example.recipes.data.model.Card

interface CardsRepositoryInterface {
    interface OnCardDisplayListener{
        fun setCardList(cardList: List<Card>?)
        fun onError(errorMessageText: String?)
    }

    fun getCards(listener: OnCardDisplayListener)
}