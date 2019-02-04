package com.example.recipes

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }
}