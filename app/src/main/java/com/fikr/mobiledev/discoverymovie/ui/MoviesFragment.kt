package com.fikr.mobiledev.discoverymovie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fikr.mobiledev.discoverymovie.R
import com.fikr.mobiledev.discoverymovie.adapter.MovieAdapter
import com.fikr.mobiledev.discoverymovie.databinding.GenericListBinding
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.generic_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment(private val isFavorite: Boolean) : Fragment() {

    private lateinit var binding: GenericListBinding
    private lateinit var adapter: MovieAdapter
    private val viewModel by viewModel<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.generic_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup rv list
        setupList()

        //subscribe
        if (isFavorite) viewModel.loadFavoriteMovies().observe(this, loadMoviesLoadedObserver())
        else {
            viewModel.loadMovies().observe(this, loadMoviesLoadedObserver())
            viewModel.onLoading()?.observe(this, onLoadingObserver())
            viewModel.onError()?.observe(this, onErrorObserver())
        }

        //refresh fetch data
        binding.btnRetry.setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun setupList() {
        adapter = MovieAdapter(object : MovieAdapter.OnMovieClicked {
            override fun onClick(position: Int) {
                val intent = Intent(activity?.baseContext, DetailActivity::class.java)
                intent.putExtra("EXTRA_MOVIE", adapter.currentList?.get(position))
                startActivity(intent)
            }
        })
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvList.layoutManager = staggeredGridLayoutManager
        binding.rvList.adapter = adapter
    }

    private fun onLoadingObserver(): Observer<Boolean> {
        return Observer {
            Log.e("loading...", it.toString())
            if (it) {
                loading.visibility = View.VISIBLE
                binding.errorLayout.visibility = View.GONE
                binding.emptyLayout.visibility = View.GONE
                binding.rvList.visibility = View.GONE
                loading.startShimmer()
            } else {
                loading.stopShimmer()
                loading.visibility = View.GONE
            }

        }
    }

    private fun onErrorObserver(): Observer<String> {
        return Observer {
            Log.e("error", it)
            binding.errorLayout.visibility = View.VISIBLE
            binding.emptyLayout.visibility = View.GONE
            binding.rvList.visibility = View.GONE
        }
    }

    private fun loadMoviesLoadedObserver(): Observer<PagedList<Movie>> {
        return Observer {
            if (it.isEmpty() && viewModel.onError()?.value == null) {
                binding.rvList.visibility = View.GONE
                binding.emptyLayout.visibility = View.VISIBLE
                return@Observer
            }
            binding.emptyLayout.visibility = View.GONE
            binding.rvList.visibility = View.VISIBLE
            adapter.submitList(it)
        }
    }
}