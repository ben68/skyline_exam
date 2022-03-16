package com.skyline.practice.ui.song

import androidx.lifecycle.MutableLiveData
import com.skyline.practice.data.source.local.Song
import com.skyline.practice.data.source.local.SongNote
import com.skyline.practice.data.AppRepository
import com.skyline.practice.ui.base.RxViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SongViewModel(private val repository: AppRepository) : RxViewModel() {

    val song = MutableLiveData<Song>()
    private val note = MutableLiveData<SongNote>()

    fun getData(id: String) {
        observe {
            repository.getSong(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    song.value = it
                }
                .subscribe()
        }

        observe {
            repository.getSongNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .onErrorReturn {
                    SongNote(songId = id)
                        .apply {
                            repository.newNote(this)
                            note.postValue(this)
                        }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    note.value = it
                }
                .subscribe()
        }
    }
}