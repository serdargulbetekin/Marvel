package com.foreks.android.marvel.application

import android.content.Context
import com.foreks.android.marvel.di.AppComponent
import com.foreks.android.marvel.di.AppModule
import com.foreks.android.marvel.di.DaggerAppComponent
import com.foreks.android.marvel.di.MarvelRepoModule


object AppConfig {

    private lateinit var context: Context
    lateinit var appComponent: AppComponent


    fun init(context: Context) {
        this.context = context
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(AppConfig.context))
            .build()

    }
}
