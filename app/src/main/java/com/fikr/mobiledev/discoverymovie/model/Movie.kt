package com.fikr.mobiledev.discoverymovie.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @SerializedName(value = "original_title", alternate = ["original_name"])
    @PrimaryKey
    val name: String,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("overview")
    val description: String,
    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,
    var type: String
) : Parcelable
