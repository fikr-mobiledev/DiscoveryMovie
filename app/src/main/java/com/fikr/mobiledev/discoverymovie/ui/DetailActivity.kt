package com.fikr.mobiledev.discoverymovie.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.fikr.mobiledev.discoverymovie.BuildConfig
import com.fikr.mobiledev.discoverymovie.R
import com.fikr.mobiledev.discoverymovie.databinding.ActivityDetailBinding
import com.fikr.mobiledev.discoverymovie.model.Movie
import com.fikr.mobiledev.discoverymovie.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModel<DetailViewModel>()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val movie: Movie? = intent.extras?.getParcelable("EXTRA_MOVIE")
        movie?.let {
            viewModel.setDetail(it)
            bind(viewModel.getDetail())
            viewModel.isFavorite().observe(this, favoriteObserver())
            viewModel.checkIsFavorite(it.name)
            changeFabFavoriteIcon(it.isFavorite)
        }

        binding.fabFavorite.setOnClickListener {
            movie?.let {
                if (isFavorite) {
                    isFavorite = false
                    it.isFavorite = isFavorite
                    viewModel.unmarkAsFavorite(it)
                } else {
                    isFavorite = true
                    it.isFavorite = isFavorite
                    viewModel.markAsFavorite(it)
                }
            }
        }
    }

    private fun bind(movie: Movie) {
        movie.poster?.let {
            Glide.with(binding.root.context).load(BuildConfig.BASE_URL_IMAGE + it)
                .into(binding.ivPoster)
        }
        binding.movie = movie
    }

    private fun favoriteObserver(): Observer<Boolean> {
        return Observer {
            isFavorite = it
            changeFabFavoriteIcon(it)
        }
    }

    private fun changeFabFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite)
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        else
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}