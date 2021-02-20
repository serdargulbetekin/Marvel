package com.foreks.android.marvel.application

import android.app.Application

class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
    }
}