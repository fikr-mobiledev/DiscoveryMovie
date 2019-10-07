package com.fikr.mobiledev.discoverymovie.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class MovieDataSourceFactory(
    private val type: String
) : DataSource.Factory<Int, Movie>() {

    val dataSourceLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Movie> {
        val dataSource = MovieDataSource(type)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}