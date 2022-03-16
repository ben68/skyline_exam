package com.skyline.practice

import androidx.room.Room
import com.skyline.practice.data.source.local.SongDatabase
import com.skyline.practice.data.AppRepository
import com.skyline.practice.data.source.remote.ApiService
import com.skyline.practice.ui.home.HomeViewModel
import com.skyline.practice.ui.song.SongViewModel
import com.skyline.practice.ui.song.imageNote.ImageNoteViewModel
import com.skyline.practice.ui.song.textNote.TextNoteViewModel
import com.skyline.practice.ui.song.videoNote.VideoNoteViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModules = module {
    factory {
        provideHttpClient()
    }
    single {
        provideRetrofit(get())
    }
    factory {
        ApiService.newInstance(get())
    }
    factory {
        AppRepository(get(), get())
    }
}

val viewModules = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SongViewModel(get())
    }
    viewModel {
        TextNoteViewModel(get())
    }
    viewModel {
        ImageNoteViewModel(get())
    }
    viewModel {
        VideoNoteViewModel(get())
    }
}

val databaseModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SongDatabase::class.java,
            SongDatabase::class.java.simpleName
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    single {
        get<SongDatabase>().songDao()
    }
    single {
        get<SongDatabase>().songNoteDao()
    }
}

val moduleList = listOf(networkModules, viewModules, databaseModules)

fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .client(httpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

fun provideHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .build()
