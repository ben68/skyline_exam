package com.skyline.practice.ui.base

import android.Manifest
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.tbruyelle.rxpermissions3.RxPermissions

abstract class BaseNoteFragment<V: ViewBinding, M: ViewModel>: BaseFragment<V, M>() {

    companion object {
        private const val KEY_SONG_ID = "key_song_id"
    }

    protected lateinit var mSongId: String
    private lateinit var mPermissions: RxPermissions

    fun with(songId: String) = this.apply {
        arguments = bundleOf(KEY_SONG_ID to songId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSongId = requireArguments().getString(KEY_SONG_ID)!!
        mPermissions = RxPermissions(this)
    }

    protected fun onCameraPermissionGranted(doOnSuccess: () -> Unit) {
        mPermissions.request(Manifest.permission.CAMERA)
            .subscribe { granted ->
                if (granted) doOnSuccess()
            }
    }

    protected fun onStoragePermissionGranted(doOnSuccess: () -> Unit) {
        mPermissions.request(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe { granted ->
            if (granted) doOnSuccess()
        }
    }
}