package com.skyline.practice.ui.song.imageNote

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.skyline.practice.data.AppRepository
import com.skyline.practice.data.source.local.SongNote
import com.skyline.practice.ui.base.BaseNoteViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class ImageNoteViewModel(private val repository: AppRepository): BaseNoteViewModel(repository) {
    val uri = MutableLiveData<Uri?>()

    override fun onDataLoad(note: SongNote) {
        note.imageUri.takeUnless {
            it.isNullOrBlank()
        }?.let {
            uri.value = Uri.parse(it)
        }
    }

    fun saveImageUriToDatabase(songId: String, fileUri: Uri?) {
        isLoading.value = true
        (songNote.value?: SongNote(songId = songId))
            .apply {
                imageUri = fileUri.toString()
                observe {
                    Completable
                        .fromAction {
                            repository.newNote(this)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            uri.value = fileUri
                            isLoading.value = false
                        }
                        .subscribe()
                }
            }
    }
}
