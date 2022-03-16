package com.skyline.practice.ui.home

import androidx.lifecycle.MutableLiveData
import com.skyline.practice.data.bean.SongList
import com.skyline.practice.data.source.local.Song
import com.skyline.practice.data.AppRepository
import com.skyline.practice.ui.base.RxViewModel
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(private val repository: AppRepository): RxViewModel() {

    val data = MutableLiveData<List<Song>>()
    val isEmpty = MutableLiveData<Boolean>()

    fun getData() {
        repository.getSongListFromApi()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess {
                repository.saveSongListToDatabase(it)
            }
            .onErrorReturn {
                SongList.empty
            }
            .flatMap {
                repository.getSongListFromDatabase()
            }
            .doOnSuccess {
                data.postValue(it)
                isEmpty.postValue(it.isNullOrEmpty())
            }
            .subscribe()
            .let { mDisposables.add(it) }
    }
}