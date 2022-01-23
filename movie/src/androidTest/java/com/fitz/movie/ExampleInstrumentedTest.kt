package com.fitz.movie

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.fitz.movie.di.module.RepositoryModule
import com.fitz.movie.presentation.view.fragment.SecondFragment
import com.fitz.movie.usecase.model.MovieViewItem
import com.fitz.movie.usecase.model.MovieViewItem.Companion.SELECTED_MOVIE_ITEM
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import java.util.concurrent.TimeUnit

@UninstallModules(value = [RepositoryModule::class])
@HiltAndroidTest
class ExampleInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(HiltTestActivity::class.java)

    @Before
    fun startFragment() {
        val args = Bundle().apply {
            putSerializable(
                SELECTED_MOVIE_ITEM,
                MovieViewItem(
                    "Dune",
                    "https://image.tmdb.org/t/p/original/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
                    4.5F,
                    "A mythic and emotionally charged hero's journey, \"Dune\" tells the story of Paul Atreides, a brilliant and gifted young man born into a great destiny beyond his understanding, who must travel to the most dangerous planet in the universe to ensure the future of his family and his people.",
                    123,
                    false
                )
            )
        }
        HiltTestExt.launchFragmentInHiltContainer<SecondFragment>(fragmentArgs = args, themeResId = R.style.Theme_HiltDemo) {  }
    }

    @Test
    fun showDune() {
        BaristaSleepInteractions.sleep(TimeUnit.SECONDS.toMillis(5))

        onView(withText("Read description")).check(matches(isDisplayed()))
        onView(withText("Read description")).perform(click())

        onView(withSubstring("Paul Atreides")).check(matches(isDisplayed()))

        BaristaSleepInteractions.sleep(TimeUnit.SECONDS.toMillis(5))
    }
}