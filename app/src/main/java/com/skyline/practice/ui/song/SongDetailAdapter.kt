package com.skyline.practice.ui.song

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skyline.practice.ui.song.imageNote.ImageNoteFragment
import com.skyline.practice.ui.song.textNote.TextNoteFragment
import com.skyline.practice.ui.song.videoNote.VideoNoteFragment

class SongDetailAdapter(activity: SongActivity, private val songId: String): FragmentStateAdapter(activity) {
    override fun getItemCount() = 3
    override fun createFragment(position: Int) =
        when(position) {
            0 -> TextNoteFragment.newInstance(songId)
            1 -> ImageNoteFragment.newInstance(songId)
            else -> VideoNoteFragment.newInstance(songId)
        }
}