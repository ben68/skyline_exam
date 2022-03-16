package com.skyline.practice.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skyline.practice.data.source.local.Song
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class SongListAdapter(private val model: HomeViewModel): RecyclerView.Adapter<SongItemViewHolder>() {

    private val mClickSubject = PublishSubject.create<Song>()
    val clickEvent: Observable<Song> = mClickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
        return SongItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        model.data.value
            ?.get(position)
            ?.run {
                holder.bind(position, this)
                holder.itemView.setOnClickListener {
                    mClickSubject.onNext(this)
                }
            }
    }

    override fun getItemCount(): Int {
        return model.data.value?.count()?: 0
    }
}