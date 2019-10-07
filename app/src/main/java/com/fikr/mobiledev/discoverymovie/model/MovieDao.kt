package com.fikr.mobiledev.discoverymovie.model

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie WHERE is_favorite = :isFavorite AND type = :type")
    fun getAllFavorite(isFavorite: Boolean, type: String): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movie WHERE name = :name")
    suspend fun isFavorite(name: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Movie): Long

    @Delete
    suspend fun delete(favorite: Movie)
}