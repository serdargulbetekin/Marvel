package com.foreks.android.marvel.modules.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.foreks.android.marvel.application.AppConfig
import com.foreks.android.marvel.modules.detail.DetailActivityViewModel
import com.foreks.android.marvel.modules.main.MainActivityViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(AppConfig.appComponent.getMarvelRepo()) as T
            }modelClass.isAssignableFrom(DetailActivityViewModel::class.java) -> {
                DetailActivityViewModel(AppConfig.appComponent.getMarvelRepo()) as T
            }
            else -> throw IllegalArgumentException("No View Model Class Found!!!")
        }
    }

}