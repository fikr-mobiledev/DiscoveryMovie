package com.fikr.mobiledev.discoverymovie.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class DiscoveryMovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}