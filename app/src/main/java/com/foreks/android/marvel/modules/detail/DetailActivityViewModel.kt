package com.foreks.android.marvel.modules.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foreks.android.marvel.modules.repo.Comics
import com.foreks.android.marvel.modules.repo.MarvelRepo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DetailActivityViewModel @Inject constructor(private val repo: MarvelRepo) : ViewModel() {

    private val _mutableComicsLiveData = MutableLiveData<List<Comics>>()

    val charactersLiveData: LiveData<List<Comics>>
        get() = _mutableComicsLiveData

    private val date = "2005-01-01";
    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")



    @SuppressLint("CheckResult")
     fun getComics(id:String) {
        repo.singleComics(id).map {
            it.filter {
                it.date.after(simpleDateFormat.parse(date))
            }.sortedByDescending { it.date }
        }
            .subscribe({
                _mutableComicsLiveData.postValue(it)
            }, {
                _mutableComicsLiveData.postValue(null)
            })
    }
}
