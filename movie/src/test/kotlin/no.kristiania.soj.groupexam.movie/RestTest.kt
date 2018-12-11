package no.kristiania.soj.groupexam.movie

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import no.kristiania.soj.groupexam.movie.dto.PageDTO
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(GroupexamApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTest {
    @LocalServerPort
    var port = 0

    @Before
    @After
    fun clean() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/movies"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        val limit = 10

        while (limit > 0) {
            val listDTO = given()
                    .queryParam("limit", 10)
                    .get()
                    .then()
                    .statusCode(200)
                    .extract()
                    .`as`(PageDTO::class.java)

            listDTO.list.stream()
                    .map({ (it as Map<String, *>)["id"] })
                    .forEach {
                        given().delete("/$it")
                                .then()
                                .statusCode(204)
                    }
        }
    }

    @Test
    fun testCreateMovie() {
        val title = "title"
        val director = "dir"

        val movie = given().body(MovieDTO(title, null, null, director, null, null, null))
                .contentType(Format.JSON_V1)
                .post()
                .then()
                .statusCode(201)
                .extract().header("movie")

        given().get()
                .then()
                .statusCode(200)
                .body("list.size()", equalTo(1))
                .body("totalSize", equalTo(1))

        given().basePath("")
                .get(movie)
                .then()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("director", equalTo(director))
    }
}