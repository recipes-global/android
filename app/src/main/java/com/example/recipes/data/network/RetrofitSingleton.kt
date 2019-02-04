package com.example.recipes.data.network

import com.example.recipes.utils.Constant
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitSingleton {
    private var retrofit: Retrofit? = null

    fun createRetrofitInstance(): Retrofit?{
        if(retrofit == null){
            val client: OkHttpClient
            val builder= OkHttpClient.Builder()
                .readTimeout(Constant.GLOBAL_TIMEOUT_PARAMETER, TimeUnit.SECONDS)
                .connectTimeout(Constant.GLOBAL_TIMEOUT_PARAMETER, TimeUnit.SECONDS)

            client = builder.build()

            val gson= GsonBuilder().create()

            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}