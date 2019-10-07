//package com.fikr.mobiledev.discoverymovie
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.fikr.mobiledev.discoverymovie.model.Movie
//import com.fikr.mobiledev.discoverymovie.repository.DiscoveryMovieRepository
//import com.fikr.mobiledev.discoverymovie.viewmodel.TvShowsViewModel
//import kotlinx.coroutines.runBlocking
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.ArgumentMatchers
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import java.io.IOException
//
//class TvShowsViewModelTest {
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    @Mock
//    private val tvShowsObserver: Observer<ArrayList<Movie>> =
//        mock(Observer::class.java) as Observer<ArrayList<Movie>>
//
//    @Mock
//    private val loadingObserver: Observer<Boolean> =
//        mock(Observer::class.java) as Observer<Boolean>
//
//    @Mock
//    private val errorObserver: Observer<String> =
//        mock(Observer::class.java) as Observer<String>
//
//    @Mock
//    private val repository: DiscoveryMovieRepository =
//        mock(DiscoveryMovieRepository::class.java)
//    private val viewModel: TvShowsViewModel = TvShowsViewModel(repository)
//
//    @Before
//    fun setup() {
//        viewModel.onLoading().observeForever(loadingObserver)
//        viewModel.onError().observeForever(errorObserver)
//        viewModel.onTvShowsLoaded().observeForever(tvShowsObserver)
//    }
//
//    @Test
//    fun `successfully load tvshows`() = runBlocking {
//        verify(loadingObserver).onChanged(true)
//        `when`(repository.loadTvShows()).thenCallRealMethod()
//        viewModel.loadTvShows()
//        verify(repository).loadTvShows()
//        verify(tvShowsObserver).onChanged(ArgumentMatchers.isNotNull())
//        verifyZeroInteractions(errorObserver)
//        verify(loadingObserver).onChanged(false)
//    }
//
//    @Test
//    fun `successfully load tvshows with empty data`() = runBlocking {
//        val movies = ArrayList<Movie>()
//        verify(loadingObserver).onChanged(true)
//        `when`(repository.loadTvShows()).thenReturn(movies)
//        viewModel.loadTvShows()
//        verify(repository).loadTvShows()
//        verify(tvShowsObserver).onChanged(movies)
//        assertEquals(0, viewModel.onTvShowsLoaded().value?.size)
//        verifyZeroInteractions(errorObserver)
//        verify(loadingObserver).onChanged(false)
//    }
//
//    @Test
//    fun `show and hide loading while load tvshows`() = runBlocking {
//        verify(loadingObserver).onChanged(true)
//        viewModel.loadTvShows()
//        verify(repository).loadTvShows()
//        verify(loadingObserver).onChanged(false)
//    }
//
//    @Test
//    fun `connection was interrupted when load tvshows and error must not null`() = runBlocking {
//        verify(loadingObserver).onChanged(true)
//        `when`(repository.loadTvShows())
//            .thenAnswer { throw IOException("Your connection was interrupted") }
//        viewModel.loadTvShows()
//        verify(repository).loadTvShows()
//        verify(errorObserver).onChanged("Your connection was interrupted")
//        verify(loadingObserver).onChanged(false)
//    }
//}