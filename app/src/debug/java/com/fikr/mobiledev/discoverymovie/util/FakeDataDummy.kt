package com.fikr.mobiledev.discoverymovie.util

import com.fikr.mobiledev.discoverymovie.model.Movie

class FakeDataDummy {

    companion object {
        fun getDummyMovies(isFavorite: Boolean, type: String): List<Movie> {
            val movies = ArrayList<Movie>()
            movies.add(
                Movie(
                    "Spider-Man: Far from Home",
                    "/lcq8dVxeeOqHvvgcte707K0KVx5.jpg",
                    "Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.",
                    "en",
                    isFavorite, type
                )
            )
            movies.add(
                Movie(
                    "The Old Man & the Gun",
                    "/zBhv8rsLOfpFW2M5b6wW78Uoojs.jpg",
                    "A crew of savvy former strip club employees band together to turn the tables on their Wall Street clients.",
                    "en",
                    isFavorite,
                    type
                )
            )
            return movies
        }
    }
}