package com.fikr.mobiledev.discoverymovie

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.fikr.mobiledev.discoverymovie.di.dbModuleTest
import com.fikr.mobiledev.discoverymovie.di.utilModule
import com.fikr.mobiledev.discoverymovie.di.viewModelModule
import com.fikr.mobiledev.discoverymovie.model.DiscoveryMovieDatabase
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository
import com.fikr.mobiledev.discoverymovie.viewmodel.MoviesViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.Mockito.*

class MoviesViewModelTest : KoinTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private val moviesObserver: Observer<PagedList<Movie>> =
        mock(Observer::class.java) as Observer<PagedList<Movie>>

    @Mock
    private val loadingObserver: Observer<Boolean> =
        mock(Observer::class.java) as Observer<Boolean>

    @Mock
    private val errorObserver: Observer<String> =
        mock(Observer::class.java) as Observer<String>

    private val repository: DiscoveryMovieRepository by inject()

    private val viewModel: MoviesViewModel by inject()

    private val db: DiscoveryMovieDatabase by inject()

    @Before
    fun setup() {
        startKoin {
            androidContext(mock(Application::class.java))
            modules(listOf(dbModuleTest, viewModelModule, utilModule))
        }
        declareMock<DiscoveryMovieRepository>()
    }

    @After
    fun tearDown() {
        db.close()
        stopKoin()
    }

    @Test
    fun successfullyLoadMovies() {
        val movies = emptyList<Movie>()
        `when`(repository.loadMovies("1")).thenReturn(movies)
        viewModel.loadMovies().observeForever(moviesObserver)
        viewModel.onLoading()?.observeForever(loadingObserver)
        viewModel.onError()?.observeForever(errorObserver)

        //loading done
        verify(loadingObserver).onChanged(false)
        viewModel.onLoading()?.value?.let { assertFalse(it) }

        //without error
        verifyZeroInteractions(errorObserver)
        viewModel.onError()?.value?.let { assertNull(it) }

        //movies not null
        verify(moviesObserver).onChanged(isNotNull())
    }

    @Test
    fun failedLoadMoviesWithError() = runBlocking {
        `when`(repository.loadMovies("1")).thenAnswer { throw Exception("Your connection was interrupted") }
        viewModel.loadMovies().observeForever(moviesObserver)
        viewModel.onLoading()?.observeForever(loadingObserver)
        viewModel.onError()?.observeForever(errorObserver)

        //loading done
        verify(loadingObserver).onChanged(false)
        viewModel.onLoading()?.value?.let { assertFalse(it) }

        //with error
        verify(errorObserver).onChanged("Your connection was interrupted")
        viewModel.onError()?.value?.let { assertNotNull(it) }
        viewModel.onError()?.value?.let { assertSame(it, "Your connection was interrupted") }

        //movies must null
        assertNull(viewModel.loadMovies().value)
    }

    @Test
    fun successfullyLoadFavoriteMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java)
        `when`(repository.loadFavoriteMovies()).thenReturn(dataSourceFactory as DataSource.Factory<Int, Movie>)

        viewModel.loadFavoriteMovies().observeForever(moviesObserver)

        //movies not null
        verify(moviesObserver).onChanged(isNotNull())
    }

}