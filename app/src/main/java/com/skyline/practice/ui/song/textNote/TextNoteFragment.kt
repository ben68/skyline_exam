package com.skyline.practice.ui.song.textNote

import android.os.Bundle
import android.view.View
import com.hi.dhl.binding.viewbind
import com.skyline.practice.databinding.FragmentTextNoteBinding
import com.skyline.practice.ui.base.BaseNoteFragment
import org.koin.android.viewmodel.ext.android.viewModel

class TextNoteFragment: BaseNoteFragment<FragmentTextNoteBinding, TextNoteViewModel>() {

    companion object {
        fun newInstance(songId: String) =
            TextNoteFragment().with(songId)
    }

    override val mBinding: FragmentTextNoteBinding by viewbind()
    override val mModel: TextNoteViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(mBinding) {
            editButton.setOnClickListener {
                text.visibility = View.GONE
                input.visibility = View.VISIBLE
                editButton.visibility = View.GONE
                okButton.visibility = View.VISIBLE
                input.setText(text.text)
            }

            okButton.setOnClickListener {
                text.visibility = View.VISIBLE
                input.visibility = View.GONE
                editButton.visibility = View.VISIBLE
                okButton.visibility = View.GONE
                mModel.writeTextNote(mSongId, input.text.toString())
            }
        }

        with(mModel) {
            isLoading.observe(viewLifecycleOwner, {
                with(mBinding) {
                    editButton.isEnabled = !it
                    okButton.isEnabled = !it
                }
            })

            text.observe(viewLifecycleOwner, {
                mBinding.text.text = it
            })

            getData(mSongId)
        }
    }

}