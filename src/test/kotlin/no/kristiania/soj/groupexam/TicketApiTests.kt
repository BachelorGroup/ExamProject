package no.kristiania.soj.groupexam

import io.restassured.RestAssured
import io.restassured.http.ContentType
import no.kristiania.soj.groupexam.dto.TicketDTO
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

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(GroupexamApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketApiTests {

    @LocalServerPort
    protected var port = 0

    @Before
    @After
    fun clean() {

        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/api/ticket"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

        /*
           Here, we read each resource (GET), and then delete them
           one by one (DELETE)
         */
        val list = RestAssured.given().accept(ContentType.JSON).get()
                .then()
                .statusCode(200)
                .extract()
                .`as`(Array<TicketDTO>::class.java)
                .toList()


        /*
            Code 204: "No Content". The server has successfully processed the request,
            but the return HTTP response will have no body.
         */
        list.stream().forEach {
            RestAssured.given().pathParam("ticketId", it.ticketId)
                    .delete("/{ticketId}")
                    .then()
                    .statusCode(204)
        }

        RestAssured.given().get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))
    }

    @Test
    fun testCreateAndGet() {

        val cinema = "Colosseum"
        val hall = 1
        val seatRow = 5
        val seatColumn = 7
        val movieTitle = "Monsters INC"
        val movieDateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 18, 2, 0)

        val dto = TicketDTO(cinema, hall, seatRow, seatColumn, movieTitle, movieDateTime)

        //Should be no tickets
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        //Creating a ticket
        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        //Should be 1 ticket now
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(1))

        //1 ticket with same data as the POST
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", id)
                .get("/{ticketId}")
                .then()
                .statusCode(200)
                .body("ticketId", CoreMatchers.equalTo(id))
                .body("cinema", CoreMatchers.equalTo(cinema))
                .body("seatRow", CoreMatchers.equalTo(seatRow))
                .body("seatColumn", CoreMatchers.equalTo(seatColumn))
                .body("movieTitle", CoreMatchers.equalTo(movieTitle)) //TODO: Actually be able to check movieDateTime properly (Database reformatting the value)
    }

    @Test
    fun updateTicket() {

        val cinema = "Colosseum"
        val hall = 1
        val seatRow = 5
        val seatColumn = 7
        val movieTitle = "Monsters INC"
        val movieDateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 18, 2, 0)

        val dto = TicketDTO(cinema, hall, seatRow, seatColumn, movieTitle, movieDateTime)

        //Creating a ticket
        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val updatedMovieTitle = "New movie"

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", id)
                .body(TicketDTO(cinema, hall, seatRow, seatColumn, updatedMovieTitle, movieDateTime, null, id)) //TODO: Make it so that we don't need a null value
                .put("/{ticketId}")
                .then()
                .statusCode(204) // instead of 204

        //Check if updated correctly
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", id)
                .get("/{ticketId}")
                .then()
                .statusCode(200)
                .body("ticketId", CoreMatchers.equalTo(id))
                .body("cinema", CoreMatchers.equalTo(cinema))
                .body("seatRow", CoreMatchers.equalTo(seatRow))
                .body("seatColumn", CoreMatchers.equalTo(seatColumn))
                .body("movieTitle", CoreMatchers.equalTo(updatedMovieTitle))
    }
}