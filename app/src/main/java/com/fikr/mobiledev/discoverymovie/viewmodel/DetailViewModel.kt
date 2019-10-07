package com.fikr.mobiledev.discoverymovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DiscoveryMovieRepository) : ViewModel(){

    private lateinit var movie: Movie
    private val isFavorite: MutableLiveData<Boolean> = MutableLiveData()

    fun isFavorite(): LiveData<Boolean> {
        return isFavorite
    }

    fun setDetail(movie: Movie) {
        this.movie = movie
    }
    fun getDetail() : Movie {
        return movie
    }

    fun checkIsFavorite(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            isFavorite.postValue(repository.isFavorite(name))
        }
    }

    fun markAsFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.markAsFavorite(movie)
            isFavorite.postValue(true)
        }
    }

    fun unmarkAsFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.unmarkAsFavorite(movie)
            isFavorite.postValue(false)
        }
    }
}
