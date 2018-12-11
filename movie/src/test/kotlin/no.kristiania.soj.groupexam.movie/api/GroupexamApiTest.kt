package no.kristiania.soj.groupexam.movie.api

import org.jboss.arquillian.container.test.api.Deployment
import org.jboss.arquillian.junit.Arquillian
import org.jboss.shrinkwrap.api.ShrinkWrap
import org.jboss.shrinkwrap.api.asset.EmptyAsset
import org.jboss.shrinkwrap.api.spec.JavaArchive
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(Arquillian::class)
class GroupexamApiTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun get() {
    }

    @Test
    fun addMovie() {
    }

    companion object {
        @Deployment
        fun createDeployment(): JavaArchive {
            return ShrinkWrap.create(JavaArchive::class.java)
                    .addClass(GroupexamApi::class.java)
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        }
    }
}
