package com.skyline.practice.ui.song.imageNote

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.hi.dhl.binding.viewbind
import com.skyline.practice.createImageFile
import com.skyline.practice.databinding.FragmentImageNoteBinding
import com.skyline.practice.load
import com.skyline.practice.ui.base.BaseNoteFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.IOException

class ImageNoteFragment: BaseNoteFragment<FragmentImageNoteBinding, ImageNoteViewModel>() {

    companion object {
        const val REQUEST_IMAGE_PICK = 1
        const val REQUEST_IMAGE_CAPTURE = 2

        fun newInstance(songId: String) =
            ImageNoteFragment().with(songId)
    }

    override val mBinding: FragmentImageNoteBinding by viewbind()
    override val mModel: ImageNoteViewModel by viewModel()

    private var mImageUri: Uri? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(mBinding) {
            context?.run {
                fromLibraryButton.setOnClickListener {
                    pickImage(requireContext())
                }
                packageManager
                    ?.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
                    ?.takeIf { it }.run {
                        fromCameraButton.visibility = View.VISIBLE
                        fromCameraButton.setOnClickListener {
                            onCameraPermissionGranted {
                                takeImage(requireContext())
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
                it.let {
                    mBinding.image.load(it)
                }
            })

            getData(mSongId)
        }
    }

    private fun pickImage(context: Context) {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            resolveActivity(context.packageManager)?.also {
                startActivityForResult(
                    this,
                    REQUEST_IMAGE_PICK
                )
            }
        }
    }

    private fun takeImage(context: Context) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            resolveActivity(context.packageManager)?.also {
                mImageUri = try {
                    context.createImageFile().let {
                        FileProvider.getUriForFile(context, "com.skyline.practice", it)
                    }
                } catch (e: IOException) {
                    Toast.makeText(context, "Failed to create file!", Toast.LENGTH_SHORT).show()
                    null
                }

                mImageUri?.let {
                    putExtra(MediaStore.EXTRA_OUTPUT, it)
                    startActivityForResult(this, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when(requestCode) {
                REQUEST_IMAGE_PICK -> data?.data
                REQUEST_IMAGE_CAPTURE -> mImageUri
                else -> null
            }.let {
                mModel.saveImageUriToDatabase(mSongId, it)
            }
        }
    }
}