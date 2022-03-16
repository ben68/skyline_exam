package com.skyline.practice.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skyline.practice.R
import com.skyline.practice.data.source.local.Song
import com.skyline.practice.databinding.ItemSongBinding
import com.skyline.practice.load

class SongItemViewHolder(private val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int, data: Song) {
        with(binding) {
            icon.load(
                url = data.imageUrl,
                placeholder = R.drawable.ic_launcher_background
            )
            title.text = data.name
            artist.text = data.artist
            number.text = (position + 1).toString()
        }
    }

    companion object {
        fun from(parent: ViewGroup): SongItemViewHolder {
            val inflater = parent.context.applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            return SongItemViewHolder(ItemSongBinding.inflate(inflater, parent, false ))
        }
    }
}