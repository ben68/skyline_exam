package com.skyline.practice.ui.base

import androidx.lifecycle.MutableLiveData
import com.skyline.practice.data.source.local.SongNote
import com.skyline.practice.data.AppRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseNoteViewModel(private val repository: AppRepository): RxViewModel() {
    protected val songNote = MutableLiveData<SongNote>()

    abstract fun onDataLoad(note: SongNote)

    fun getData(songId: String) {
        isLoading.value = true
        observe {
            repository.getSongNote(songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { note ->
                    songNote.value = note
                    onDataLoad(note)
                    isLoading.value = false
                }
                .subscribe()
        }
    }
}