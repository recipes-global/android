package com.example.recipes.data.network

import com.example.recipes.data.model.Card
import io.reactivex.Single
import retrofit2.http.GET

interface CardAPI {

    @GET("/cards")
    fun getCards(): Single<List<Card>>
}