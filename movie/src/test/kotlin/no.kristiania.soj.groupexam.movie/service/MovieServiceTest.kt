package no.kristiania.soj.groupexam.movie.service

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(Arquillian::class)
class MovieServiceTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMovie() {
    }

    @Test
    fun createMovie() {
    }

    @Test
    fun deleteMovie() {
    }

    @Test
    fun getMoviesByDirector() {
    }

    @Test
    fun getMoviesByRating() {
    }

    @Test
    fun getMoviesByDate() {
    }

    @Test
    fun getEntityManager() {
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                    .addClass(MovieService::class.java)
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
