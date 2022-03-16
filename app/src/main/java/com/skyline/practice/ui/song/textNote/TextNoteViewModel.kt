package com.skyline.practice.ui.song.textNote

import androidx.lifecycle.MutableLiveData
import com.skyline.practice.data.AppRepository
import com.skyline.practice.data.source.local.SongNote
import com.skyline.practice.ui.base.BaseNoteViewModel
import com.skyline.practice.ui.base.RxViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TextNoteViewModel(private val repository: AppRepository): BaseNoteViewModel(repository) {
    val text = MutableLiveData<String?>()

    override fun onDataLoad(note: SongNote) {
        text.value = note.text
    }

    fun writeTextNote(songId: String, content: String) {
        isLoading.value = true
        observe {
            repository.getSongNote(songId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess {
                    it.text = content
                    repository.newNote(it)
                    text.postValue(content)
                    isLoading.postValue(false)
                }
                .subscribe()
        }
    }
}
