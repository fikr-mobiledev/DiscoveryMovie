package com.fikr.mobiledev.discoverymovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.model.MovieDataSource
import com.fikr.mobiledev.discoverymovie.model.MovieDataSourceFactory
import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository

class TvShowsViewModel(private val repository: DiscoveryMovieRepository) : ViewModel() {

    private var loading: LiveData<Boolean>? = null
    private var error: LiveData<String>? = null
    private lateinit var tvShows: LiveData<PagedList<Movie>>
    private lateinit var tvDataSourceFactory: MovieDataSourceFactory

    fun onLoading(): LiveData<Boolean>? {
        return loading
    }

    fun onError(): LiveData<String>? {
        return error
    }

    fun loadTvShows(): LiveData<PagedList<Movie>> {
        tvDataSourceFactory = MovieDataSourceFactory("tv_shows")
        tvShows = LivePagedListBuilder(tvDataSourceFactory, 10).build()
        loading = Transformations.switchMap(
            tvDataSourceFactory.dataSourceLiveData,
            MovieDataSource::initialLoading
        )
        error = Transformations.switchMap(
            tvDataSourceFactory.dataSourceLiveData,
            MovieDataSource::error
        )
        return tvShows
    }

    fun refresh() {
        tvDataSourceFactory.dataSourceLiveData.value?.invalidate()
    }

    fun loadFavoriteTvShows(): LiveData<PagedList<Movie>> {
        tvShows = LivePagedListBuilder(repository.loadFavoriteTvShows(), 10).build()
        return tvShows
    }

}