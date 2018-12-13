package no.kristiania.soj.groupexam.movie.api

import io.restassured.RestAssured
import io.restassured.http.ContentType
import no.kristiania.soj.groupexam.movie.MovieApplication
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import java.time.ZonedDateTime


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(MovieApplication::class)],
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

    @Test
    fun testCreateMovie() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = ZonedDateTime.now()

        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(1))
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(title))
                .body("director", CoreMatchers.equalTo(director))
                .body("description", CoreMatchers.equalTo(description))
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(rating))
                .body("releaseDate", CoreMatchers.equalTo(releaseDate))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdate() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = ZonedDateTime.now()

        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val titleNew = "titleNew"
        val directorNew = "directorNew"
        val descriptionNew = "descriptionNew"
        val infoNew = "infoNew"
        val ratingNew = 3
        val releaseDateNew = ZonedDateTime.now()

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .body(MovieDTO(
                        title = titleNew,
                        director = directorNew,
                        description = descriptionNew,
                        info = infoNew,
                        rating = ratingNew,
                        releaseDate = releaseDateNew,
                        id = id))
                .put("/{id}")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(titleNew))
                .body("director", CoreMatchers.equalTo(directorNew))
                .body("description", CoreMatchers.equalTo(descriptionNew))
                .body("info", CoreMatchers.equalTo(infoNew))
                .body("rating", CoreMatchers.equalTo(ratingNew))
                .body("releaseDate", CoreMatchers.equalTo(releaseDateNew))
                .body("id", CoreMatchers.equalTo(id))
    }
}