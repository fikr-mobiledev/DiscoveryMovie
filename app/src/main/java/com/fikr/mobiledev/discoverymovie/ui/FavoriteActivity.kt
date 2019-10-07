package com.fikr.mobiledev.discoverymovie.ui

import android.os.Bundle
import com.fikr.mobiledev.discoverymovie.R
import com.fikr.mobiledev.discoverymovie.adapter.TabAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : BaseActivity() {
    override val tabAdapter: TabAdapter
        get() = TabAdapter(true, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tvTitle.text = getString(R.string.favorite)
        binding.fabFavorite.hide()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}