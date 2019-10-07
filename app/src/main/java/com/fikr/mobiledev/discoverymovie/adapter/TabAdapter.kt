package com.fikr.mobiledev.discoverymovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fikr.mobiledev.discoverymovie.ui.MoviesFragment
import com.fikr.mobiledev.discoverymovie.ui.TvShowsFragment

class TabAdapter(private val isFavorite: Boolean, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment(isFavorite)
            1 -> TvShowsFragment(isFavorite)
            else -> throw NullPointerException("can't find desired fragment")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Movies"
            1 -> "Tv Shows"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }

}