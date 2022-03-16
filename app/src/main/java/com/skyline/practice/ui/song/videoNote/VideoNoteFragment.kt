package com.skyline.practice.ui.song.videoNote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.hi.dhl.binding.viewbind
import com.skyline.practice.databinding.FragmentVideoNoteBinding
import com.skyline.practice.ui.base.BaseNoteFragment
import org.koin.android.viewmodel.ext.android.viewModel

class VideoNoteFragment : BaseNoteFragment<FragmentVideoNoteBinding, VideoNoteViewModel>() {

    companion object {
        const val REQUEST_VIDEO_PICK = 1
        const val REQUEST_VIDEO_RECORD = 2

        fun newInstance(songId: String) =
            VideoNoteFragment().with(songId)
    }

    override val mBinding: FragmentVideoNoteBinding by viewbind()
    override val mModel: VideoNoteViewModel by viewModel()

    private lateinit var player: SimpleExoPlayer

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        context?.run {
            player = SimpleExoPlayer.Builder(this).build()

            with(mBinding) {
                videoView.player = player

                fromLibraryButton.setOnClickListener {
                    pickVideo(requireContext())
                }

                packageManager
                    ?.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
                    ?.takeIf { it }.run {
                        fromCameraButton.visibility = View.VISIBLE
                        fromCameraButton.setOnClickListener {
                            onCameraPermissionGranted {
                                takeVideo(requireContext())
                            }
                        }
                    }
            }
        }

        with(mModel) {
            isLoading.observe(viewLifecycleOwner, {
                with(mBinding) {
                    fromLibraryButton.isEnabled = !it
                    fromCameraButton.isEnabled = !it
                }
            })

            uri.observe(viewLifecycleOwner, {
                it?.let {
                    onStoragePermissionGranted {
                        playVideo(it)
                    }
                }
            })

            getData(mSongId)
        }
    }

    private fun playVideo(uri: Uri) {
        player.apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            play()
        }
    }

    private fun pickVideo(context: Context) {
        Intent().apply {
            action = Intent.ACTION_PICK
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            type = "video/*"
            resolveActivity(context.packageManager)?.also {
                startActivityForResult(this, REQUEST_VIDEO_PICK)
            }
        }
    }

    private fun takeVideo(context: Context) {
        Intent().apply {
            action = MediaStore.ACTION_VIDEO_CAPTURE
            resolveActivity(context.packageManager)?.also {
                startActivityForResult(this, REQUEST_VIDEO_RECORD)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            mModel.saveVideoUriToDatabase(mSongId, data?.data)
        }
    }
}
