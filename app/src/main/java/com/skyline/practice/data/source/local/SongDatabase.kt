package com.skyline.practice.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Song::class, SongNote::class], version = 1, exportSchema = false)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun songNoteDao(): SongNoteDao
}