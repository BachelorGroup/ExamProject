package no.kristiania.soj.groupexam.db

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class CinemaTest {

    @Before
    fun setUp() {
        val cinema: Cinema? = null
        val movie1: Movie? = null

        movie1!!.title = "test"
        movie1.director = "test testerson"
        movie1.description = "test tested"
        movie1.rating = 5
        movie1.releaseDate = "January 2018"

        val movies: MutableList<Movie> = mutableListOf(movie1)
        val movieList: List<Movie> = movies

        cinema!!.name = "test"
        cinema.halls = "testHalls"
        cinema.movies = movieList
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getName() {
    }

    @Test
    fun setName() {
    }

    @Test
    fun getMovies() {
    }

    @Test
    fun setMovies() {
    }

    @Test
    fun getHalls() {
    }

    @Test
    fun setHalls() {
    }

    @Test
    fun getId() {
    }

    @Test
    fun setId() {
    }
}