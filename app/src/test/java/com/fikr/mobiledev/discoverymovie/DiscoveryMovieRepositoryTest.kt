package com.fikr.mobiledev.discoverymovie

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DiscoveryMovieRepositoryTest {
    @Test
    fun loadMovies() {
        val response = AndroidNetworking.get(BuildConfig.BASE_URL + "discover/movie")
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .setPriority(Priority.LOW)
            .build()
            .executeForString()

        assertNotNull(response)
        assertTrue(response.isSuccess)

        val json = JsonParser().parse(response.result as String) as JsonObject
        val results = json.get("results") as JsonArray

        val movies = Gson().fromJson(
            results,
            (object : TypeToken<List<Movie>>() {}.type)
        ) as ArrayList<Movie>

        assertNotNull(movies)
    }

    @Test
    fun loadTvShows() {
        val response = AndroidNetworking.get(BuildConfig.BASE_URL + "discover/tv")
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .setPriority(Priority.LOW)
            .build()
            .executeForString()

        assertNotNull(response)
        assertTrue(response.isSuccess)

        val json = JsonParser().parse(response.result as String) as JsonObject
        val results = json.get("results") as JsonArray

        val tvShows = Gson().fromJson(
            results,
            (object : TypeToken<List<Movie>>() {}.type)
        ) as ArrayList<Movie>

        assertNotNull(tvShows)
    }
}