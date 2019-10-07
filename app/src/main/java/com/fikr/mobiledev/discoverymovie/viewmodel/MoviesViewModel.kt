package com.fikr.mobiledev.discoverymovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.model.MovieDataSource
import com.fikr.mobiledev.discoverymovie.model.MovieDataSourceFactory
import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository

class MoviesViewModel(val repository: DiscoveryMovieRepository) : ViewModel() {

    private var loading: LiveData<Boolean>? = null
    private var error: LiveData<String>? = null
    private lateinit var movies: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun onLoading(): LiveData<Boolean>? {
        return loading
    }

    fun onError(): LiveData<String>? {
        return error
    }

    fun loadMovies(): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory("movies")
        movies = LivePagedListBuilder(moviesDataSourceFactory, 10).build()
        loading = Transformations.switchMap(
            moviesDataSourceFactory.dataSourceLiveData,
            MovieDataSource::initialLoading
        )
        error = Transformations.switchMap(
            moviesDataSourceFactory.dataSourceLiveData,
            MovieDataSource::error
        )
        return movies
    }

    fun refresh() {
        moviesDataSourceFactory.dataSourceLiveData.value?.invalidate()
    }

    fun loadFavoriteMovies(): LiveData<PagedList<Movie>> {
        movies = LivePagedListBuilder(repository.loadFavoriteMovies(), 10).build()
        return movies
    }

}