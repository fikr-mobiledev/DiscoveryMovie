package com.fikr.mobiledev.discoverymovie.repository

import androidx.paging.DataSource
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.fikr.mobiledev.discoverymovie.BuildConfig
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.model.MovieDao
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.IOException

class DiscoveryMovieRepository(val movieDao: MovieDao) {

    fun loadMovies(page: String): List<Movie> {
        val response = AndroidNetworking.get(BuildConfig.BASE_URL + "discover/movie")
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("page", page)
            .setPriority(Priority.LOW)
            .build()
            .executeForString()

        if (response.isSuccess) {
            val json = JsonParser().parse(response.result as String) as JsonObject
            val results = json.get("results") as JsonArray
            val movies = Gson().fromJson(
                results,
                (object : TypeToken<List<Movie>>() {}.type)
            ) as List<Movie>
            movies.forEach {
                it.type = "movies"
            }
            return movies
        }
        throw IOException(response.error)

    }

    fun loadTvShows(page: String): List<Movie> {
        val response = AndroidNetworking.get(BuildConfig.BASE_URL + "discover/tv")
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("page", page)
            .setPriority(Priority.LOW)
            .build().executeForString()

        if (response.isSuccess) {
            val json = JsonParser().parse(response.result as String) as JsonObject
            val results = json.get("results") as JsonArray
            val tvShows = Gson().fromJson(
                results,
                (object : TypeToken<List<Movie>>() {}.type)
            ) as List<Movie>
            tvShows.forEach {
                it.type = "tv_shows"
            }
            return tvShows
        }

        throw IOException(response.error)
    }

    fun loadFavoriteMovies(): DataSource.Factory<Int, Movie> = movieDao.getAllFavorite(true, "movies")
    fun loadFavoriteTvShows(): DataSource.Factory<Int, Movie> = movieDao.getAllFavorite(true, "tv_shows")
    suspend fun isFavorite(name: String): Boolean {
        if(movieDao.isFavorite(name) == null){
            return false
        }
        return true
    }
    suspend fun markAsFavorite(favorite: Movie) = movieDao.insert(favorite)
    suspend fun unmarkAsFavorite(favorite: Movie) = movieDao.delete(favorite)
}