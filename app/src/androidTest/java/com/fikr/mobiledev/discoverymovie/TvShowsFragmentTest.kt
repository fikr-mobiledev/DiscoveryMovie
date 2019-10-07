package com.fikr.mobiledev.discoverymovie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.rule.ActivityTestRule
import com.fikr.mobiledev.discoverymovie.adapter.MovieAdapter
import com.fikr.mobiledev.discoverymovie.testing.SingleFragmentActivity
import com.fikr.mobiledev.discoverymovie.ui.TvShowsFragment
import com.fikr.mobiledev.discoverymovie.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TvShowsFragmentTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(SingleFragmentActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
        rule.activity.setFragment(TvShowsFragment())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun loadTvShows() {
        onView(ViewMatchers.withId(R.id.rv_list))
            .check(ViewAssertions.matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.rv_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieViewHolder>(
                0,
                click()
            )
        )
    }
}