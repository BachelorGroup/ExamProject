package no.kristiania.soj.groupexam.userservice

import io.restassured.RestAssured
import no.kristiania.soj.groupexam.userservice.db.UserDetailsRepository
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(UserApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDetailsApiTests {

    @LocalServerPort
    protected var port = 0
    private var path = "/api"

    @Autowired
    private lateinit var repository: UserDetailsRepository

    @Before
    @After
    fun clean() {

        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = path
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        repository.deleteAll()
    }


    @Test
    fun testOpenCount() {

        val count = RestAssured.given()
                .get("/userDetailsCount")
                .then()
                .statusCode(200)
                .extract().body().asString().toInt()

        Assert.assertEquals(0, count)
    }
}