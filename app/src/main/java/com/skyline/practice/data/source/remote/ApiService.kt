package com.skyline.practice.data.source.remote

import com.skyline.practice.data.bean.SongList
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET

interface ApiService {

    companion object {

        const val BASE_URL: String = "https://rss.itunes.apple.com/api/v1/"

        fun newInstance(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("us/itunes-music/top-songs/all/100/explicit.json")
    fun getTop100SongList(): Single<SongList>
}