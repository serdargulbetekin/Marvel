package com.foreks.android.marvel.modules.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreks.android.marvel.modules.repo.MarvelCharacter
import com.foreks.android.marvel.modules.repo.MarvelRepo
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repo: MarvelRepo) : ViewModel() {

    private val _mutableCharactersLiveData = MutableLiveData<List<MarvelCharacter>>()

    val charactersLiveData: LiveData<List<MarvelCharacter>>
        get() = _mutableCharactersLiveData

    init {
        getFavList()
    }

    @SuppressLint("CheckResult")
    private fun getFavList() {
        repo.singleMarvelCharacters()
            .subscribe({
                _mutableCharactersLiveData.postValue(it)
            }, {
                _mutableCharactersLiveData.postValue(null)
            })
    }
}