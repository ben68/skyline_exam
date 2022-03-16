package com.skyline.practice.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SongDaoTest {

    private lateinit var db: SongDatabase
    private lateinit var dao: SongDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            SongDatabase::class.java
        ).build()
        dao = db.songDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAll_empty() {
        val song =
            dao.getAll()
                .subscribeOn(Schedulers.io())
                .blockingGet()

        assertThat(song.size, `is`(0))
    }

    @Test
    fun getAll_addAndRead() {
        val newSong = Song.empty

        val songs = Completable
            .fromAction {
                dao.insert(newSong)
            }
            .subscribeOn(Schedulers.io())
            .andThen(dao.getAll())
            .blockingGet()

        assertThat(songs.size, `is`(1))
        val song = songs[0]
        assertThat(song, `is`(newSong))
    }

    @Test
    fun getSong_noResult() {
        val emptySong = Song.empty

        val song = dao.getSong("null")
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                emptySong
            }
            .blockingGet()

        assertThat(song, `is`(emptySong))
    }
}