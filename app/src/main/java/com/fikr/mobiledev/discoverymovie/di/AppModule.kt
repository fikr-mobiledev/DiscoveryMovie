package com.fikr.mobiledev.discoverymovie.di

import androidx.room.Room
import com.fikr.mobiledev.discoverymovie.model.DiscoveryMovieDatabase
import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository
import com.fikr.mobiledev.discoverymovie.viewmodel.DetailViewModel
import com.fikr.mobiledev.discoverymovie.viewmodel.MoviesViewModel
import com.fikr.mobiledev.discoverymovie.viewmodel.TvShowsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val utilModule = module {
    single { DiscoveryMovieRepository(get()) }
    single { get<DiscoveryMovieDatabase>().movieDao() }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            DiscoveryMovieDatabase::class.java,
            "discovery_movie"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

val dbModuleTest = module {
    single {
        Room.inMemoryDatabaseBuilder(androidContext(), DiscoveryMovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { TvShowsViewModel(get()) }
}
