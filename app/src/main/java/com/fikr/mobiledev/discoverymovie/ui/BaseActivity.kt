package com.fikr.mobiledev.discoverymovie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fikr.mobiledev.discoverymovie.R
import com.fikr.mobiledev.discoverymovie.adapter.TabAdapter
import com.fikr.mobiledev.discoverymovie.databinding.ActivityMainBinding

abstract class BaseActivity : AppCompatActivity() {
    abstract val tabAdapter: TabAdapter
    protected lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.viewpager.adapter = tabAdapter
        binding.tabs.setViewPager(binding.viewpager)
    }
}