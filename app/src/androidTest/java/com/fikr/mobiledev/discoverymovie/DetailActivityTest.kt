package com.fikr.mobiledev.discoverymovie

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.fikr.mobiledev.discoverymovie.ui.DetailActivity
import com.fikr.mobiledev.discoverymovie.util.FakeDataDummy
import org.junit.Rule
import org.junit.Test


class DetailActivityTest {

    private val dummyMovie = FakeDataDummy.getDummyMovies()[0]

    @Rule
    @JvmField
    val rule: ActivityTestRule<DetailActivity> =
        object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, DetailActivity::class.java)
                result.putExtra("EXTRA_MOVIE", dummyMovie)
                return result
            }
        }

    @Test
    fun loadDetail() {
        onView(withId(R.id.iv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyMovie.name)))
        onView(withId(R.id.tv_original_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_original_language)).check(matches(withText(dummyMovie.originalLanguage)))
//         onView(withId(R.id.lbl_description)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_description)).check(!matches(isDisplayed()))
//        onView(withId(R.id.tv_description)).check(matches(withText(dummyMovie.description)))

    }
}