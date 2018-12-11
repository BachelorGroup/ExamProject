package no.kristiania.soj.groupexam.movie

import io.restassured.RestAssured
import io.restassured.http.ContentType
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(GroupexamApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class MovieTestBase {

    @LocalServerPort
    protected var port = 0

    @Before
    @After
    fun clean() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/api/movies/"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        val list = RestAssured.given().accept(ContentType.JSON).get()
                .then()
                .statusCode(200)
                .extract()
                .`as`(Array<MovieDTO>::class.java)
                .toList()

        list.stream().forEach {
            RestAssured.given().pathParam("id", it.id)
                    .delete("/{id}")
                    .then()
                    .statusCode(204)
        }

        RestAssured.given().get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))
    }
}