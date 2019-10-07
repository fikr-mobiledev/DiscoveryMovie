package com.fikr.mobiledev.discoverymovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fikr.mobiledev.discoverymovie.BuildConfig
import com.fikr.mobiledev.discoverymovie.R
import com.fikr.mobiledev.discoverymovie.databinding.ItemMovieBinding
import com.fikr.mobiledev.discoverymovie.model.Movie

class MovieAdapter(
    private val callback: OnMovieClicked
) : PagedListAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = DataBindingUtil.inflate<ItemMovieBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
        return MovieViewHolder(binding, callback)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    class MovieViewHolder(private val binding: ItemMovieBinding, private val cb: OnMovieClicked) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                cb.onClick(adapterPosition)
            }
        }

        fun bind(movie: Movie) {
            binding.movie = movie
            Glide.with(binding.root.context).load(BuildConfig.BASE_URL_IMAGE + movie.poster)
                .into(binding.ivPoster)
        }
    }

    interface OnMovieClicked {
        fun onClick(position: Int)
    }

}

private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
