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
import java.time.*
import java.time.format.DateTimeFormatter


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(MovieApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieApiTest {
    @LocalServerPort
    var port = 0

    @Before
    @After
    fun clean() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/api/movie"
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
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)

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
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdateMovie() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

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
        val releaseDateNew = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10)

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
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(ratingNew))
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdateTitle() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val titleNew = "titleNew"

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .param("title", titleNew)
                .patch("/{id}/title")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(titleNew))
                .body("director", CoreMatchers.equalTo(director))
                .body("description", CoreMatchers.equalTo(description))
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(rating))
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdateDirector() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val directorNew = "directorNew"

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .param("director", directorNew)
                .patch("/{id}/director")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(title))
                .body("director", CoreMatchers.equalTo(directorNew))
                .body("description", CoreMatchers.equalTo(description))
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(rating))
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdateDescription() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val descriptionNew = "descriptionNew"

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .param("description", descriptionNew)
                .patch("/{id}/description")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(title))
                .body("director", CoreMatchers.equalTo(director))
                .body("description", CoreMatchers.equalTo(descriptionNew))
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(rating))
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdateInfo() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val infoNew = "infoNew"

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .param("info", infoNew)
                .patch("/{id}/info")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(title))
                .body("director", CoreMatchers.equalTo(director))
                .body("description", CoreMatchers.equalTo(description))
                .body("info", CoreMatchers.equalTo(infoNew))
                .body("rating", CoreMatchers.equalTo(rating))
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

    @Test
    fun testUpdateRating() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val ratingNew = 5

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .param("rating", ratingNew)
                .patch("/{id}/rating")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("title", CoreMatchers.equalTo(title))
                .body("director", CoreMatchers.equalTo(director))
                .body("description", CoreMatchers.equalTo(description))
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(ratingNew))
                .body("releaseDate", CoreMatchers.equalTo("2018-04-20T10:10:00"))
                .body("id", CoreMatchers.equalTo(id))
    }

//    @Test
//    fun testUpdateReleaseDate() {
//        val title = "title"
//        val director = "director"
//        val description = "description"
//        val info = "info"
//        val rating = 4
//        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)
//
//
//        val dto = MovieDTO(title, director, description, info, rating, releaseDate)
//
//        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .body(dto)
//                .post()
//                .then()
//                .statusCode(201)
//                .extract().asString()
//
//        val releaseDateNew = LocalDateTime.of(2016, Month.OCTOBER, 20, 10, 10, 0)
//
//
//        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .pathParam("id", id)
//                .param("releaseDate", releaseDateNew)
//                .patch("/{id}/releaseDate")
//                .then()
//                .statusCode(204)
//
//        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .pathParam("id", id)
//                .get("/{id}")
//                .then()
//                .statusCode(200)
//                .body("title", CoreMatchers.equalTo(title))
//                .body("director", CoreMatchers.equalTo(director))
//                .body("description", CoreMatchers.equalTo(description))
//                .body("info", CoreMatchers.equalTo(info))
//                .body("rating", CoreMatchers.equalTo(rating))
//                .body("releaseDate", CoreMatchers.equalTo("2016-10-20T10:10:00"))
//                .body("id", CoreMatchers.equalTo(id))
//    }

    @Test
    fun testDeleteMovie() {
        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 4
        val releaseDate = LocalDateTime.of(2018, Month.APRIL, 20, 10, 10, 0)


        val dto = MovieDTO(title, director, description, info, rating, releaseDate)

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("id", id)
                .delete("/{id}")
                .then()
                .statusCode(204)
    }
}