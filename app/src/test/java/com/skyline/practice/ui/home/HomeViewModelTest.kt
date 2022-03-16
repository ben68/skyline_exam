package com.skyline.practice.ui.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.skyline.practice.data.bean.SongList
import com.skyline.practice.data.source.local.SongDao
import com.skyline.practice.data.source.local.SongDatabase
import com.skyline.practice.getOrAwaitValue
import com.skyline.practice.data.AppRepository
import io.reactivex.rxjava3.core.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest {
    private lateinit var model: HomeViewModel
    private lateinit var repository: AppRepository
    private lateinit var dao: SongDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun begin() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context,
            SongDatabase::class.java
        ).build()
        dao = db.songDao()

        repository = Mockito.mock(AppRepository::class.java)
        `when`(repository.getSongListFromDatabase())
            .thenReturn(dao.getAll())

        model = HomeViewModel(repository)
    }

    @Test
    fun getData_empty() {
        `when`(repository.getSongListFromApi())
            .thenReturn(Single.just(SongList.empty))
        model.getData()

        val value = model.data.getOrAwaitValue()

        assertThat(value, not((nullValue())))
        assertThat(value, `is`(emptyList()))
    }
}