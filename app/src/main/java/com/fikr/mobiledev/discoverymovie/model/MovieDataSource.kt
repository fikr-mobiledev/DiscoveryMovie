package com.fikr.mobiledev.discoverymovie.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieDataSource(private val type: String) :
    PageKeyedDataSource<Int, Movie>(), KoinComponent {


    private val repository: DiscoveryMovieRepository by inject()
    val initialLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        try {
            initialLoading.postValue(true)
            var data: List<Movie>? = null
            when (type) {
                "movies" -> data = repository.loadMovies("1")
                "tv_shows" -> data = repository.loadTvShows("1")
            }
            data?.let { callback.onResult(it, null, 2) }
        } catch (e: Exception) {
            error.postValue(e.localizedMessage)
        } finally {
            initialLoading.postValue(false)
        }


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        var data: List<Movie>? = null
        when (type) {
            "movies" -> data = repository.loadMovies(params.key.toString())
            "tv_shows" -> data = repository.loadTvShows(params.key.toString())
        }
        data?.let { callback.onResult(it, params.key + 1) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {}

}