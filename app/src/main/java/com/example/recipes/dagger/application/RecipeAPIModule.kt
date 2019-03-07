package com.example.recipes.dagger.application

import com.example.recipes.data.network.RecipeAPI
import com.example.recipes.utils.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [(NetworkModule::class)])
class RecipeAPIModule {

    @Provides
    @MyApplicationScope
    fun recipeAPI(retrofit: Retrofit): RecipeAPI{
        return retrofit.create(RecipeAPI::class.java)
    }

    @Provides
    @MyApplicationScope
    fun gson(): Gson{
        return GsonBuilder().create()
    }

    @Provides
    @MyApplicationScope
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit{
        return retrofit2.Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}