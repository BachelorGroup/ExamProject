package no.kristiania.soj.groupexam.movie.db

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

val movie = Movie? null

class MovieTest {

    @Before
    fun setUp() {
        movie!!.title = "test"
        movie.director = "test testerson"
        movie.description = "test tested"
        movie.rating = 10
        movie.releaseDate = "feb 2018"
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTitle() {
        assertTrue(movie!!.title == "test")
    }

    @Test
    fun setTitle() {
    }

    @Test
    fun getDirector() {
    }

    @Test
    fun setDirector() {
    }

    @Test
    fun getDescription() {
    }

    @Test
    fun setDescription() {
    }

    @Test
    fun getInfo() {
    }

    @Test
    fun setInfo() {
    }

    @Test
    fun getRating() {
    }

    @Test
    fun setRating() {
    }

    @Test
    fun getReleaseDate() {
    }

    @Test
    fun setReleaseDate() {
    }

    @Test
    fun getId() {
    }

    @Test
    fun setId() {
    }
}