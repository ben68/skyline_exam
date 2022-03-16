package com.skyline.practice.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SongNoteDaoTest {

    private lateinit var db: SongDatabase
    private lateinit var dao: SongNoteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            SongDatabase::class.java
        ).build()
        dao = db.songNoteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getNote_noResult() {
        val emptyNote = SongNote.empty
        val note = dao.getNote("null")
            .subscribeOn(Schedulers.io())
            .onErrorReturn {
                emptyNote
            }
            .blockingGet()

        assertThat(note, `is`(emptyNote))
    }
}