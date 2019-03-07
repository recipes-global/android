package com.example.recipes.dagger.application

import com.example.recipes.utils.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    @MyApplicationScope
    fun httpLoggingInterceptor(): HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.i(it)})
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }


    @Provides
    @MyApplicationScope
    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(Constant.GLOBAL_TIMEOUT_PARAMETER, TimeUnit.SECONDS)
            .connectTimeout(Constant.GLOBAL_TIMEOUT_PARAMETER, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    }
}