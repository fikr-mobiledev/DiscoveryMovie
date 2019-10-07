package com.fikr.mobiledev.discoverymovie.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fikr.mobiledev.discoverymovie.R
import com.fikr.mobiledev.discoverymovie.adapter.TabAdapter
import com.fikr.mobiledev.discoverymovie.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main.view.viewpager


class MainActivity : BaseActivity() {
    override val tabAdapter: TabAdapter
        get() = TabAdapter(false, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.fabFavorite.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }
}

