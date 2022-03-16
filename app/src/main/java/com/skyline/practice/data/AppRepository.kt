package com.skyline.practice.data

import com.skyline.practice.data.bean.SongList
import com.skyline.practice.data.source.local.Song
import com.skyline.practice.data.source.local.SongDatabase
import com.skyline.practice.data.source.local.SongNote
import com.skyline.practice.data.source.remote.ApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AppRepository(private val service: ApiService, private val database: SongDatabase) {

    fun getSongListFromApi(): Single<SongList> {
        return service.getTop100SongList()
    }

    fun getSongListFromDatabase(): Single<List<Song>> {
        return database.songDao().getAll()
    }

    fun saveSongListToDatabase(data: SongList): Completable {
        return data.feed.results.map {
            Song(
                id = it.id,
                name = it.name,
                artist = it.artistName,
                imageUrl = it.artworkUrl100
            )
        }.let {
            database.songDao().insertOrUpdate(it)
            Completable.complete()
        }
    }

    fun getSong(id: String): Single<Song> {
        return database.songDao().getSong(id)
    }

    fun getSongNote(songId: String): Single<SongNote> {
        return database.songNoteDao().getNote(songId)
    }

    fun newNote(note: SongNote) {
        database.songNoteDao().insertOrUpdate(note)
    }
}