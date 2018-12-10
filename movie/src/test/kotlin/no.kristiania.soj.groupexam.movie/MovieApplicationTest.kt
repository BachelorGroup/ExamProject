package no.kristiania.soj.groupexam.movie

import io.restassured.RestAssured
import no.kristiania.soj.groupexam.movie.api.MOVIE_JSON
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import org.hamcrest.CoreMatchers
import org.junit.Test

class MovieApplicationTest : MovieTestBase() {
    @Test
    fun testMovieApplication() {

        val title = "title"
        val director = "director"
        val description = "description"
        val info = "info"
        val rating = 0
        val releaseDate = "14.12.18"
        val dto = MovieDTO(title, director, description, info, rating, releaseDate, null)

        RestAssured.given().accept(MOVIE_JSON)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        val id = RestAssured.given().contentType(MOVIE_JSON)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        RestAssured.given().accept(MOVIE_JSON)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(1))

        RestAssured.given().accept(MOVIE_JSON)
                .pathParam("id", id)
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("id", CoreMatchers.equalTo(id))
                .body("title", CoreMatchers.equalTo(title))
                .body("director", CoreMatchers.equalTo(director))
                .body("description", CoreMatchers.equalTo(description))
                .body("info", CoreMatchers.equalTo(info))
                .body("rating", CoreMatchers.equalTo(rating))
                .body("releaseDate", CoreMatchers.equalTo(releaseDate))
    }
}