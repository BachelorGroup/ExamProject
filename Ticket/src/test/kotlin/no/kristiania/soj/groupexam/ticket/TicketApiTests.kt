package no.kristiania.soj.groupexam.ticket

import io.restassured.RestAssured
import io.restassured.http.ContentType
import no.kristiania.soj.groupexam.ticket.dto.TicketDTO
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
@SpringBootTest(classes = [(Application::class)],
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
        val movieDateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 18, 2)
        val expectedTime = movieDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

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
                .body("movieTitle", CoreMatchers.equalTo(movieTitle))
                .body("movieDateTime", CoreMatchers.equalTo(expectedTime))
    }

    @Test
    fun testUpdateTicket() {

        val cinema = "Colosseum"
        val hall = 1
        val seatRow = 5
        val seatColumn = 7
        val movieTitle = "Monsters INC"
        val movieDateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 18, 2, 0)
        val expectedTime = movieDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

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
                .body(TicketDTO(
                        cinema = cinema,
                        hall = hall,
                        seatRow = seatRow,
                        seatColumn = seatColumn,
                        movieTitle = updatedMovieTitle,
                        movieDateTime = movieDateTime,
                        ticketId = id))
                .put("/{ticketId}")
                .then()
                .statusCode(204)

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
                .body("movieDateTime", CoreMatchers.equalTo(expectedTime))
    }

    @Test
    fun testUpdateSeat() {

        val cinema = "Colosseum"
        val hall = 1
        val seatRow = 5
        val seatColumn = 7
        val movieTitle = "Monsters INC"
        val movieDateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 18, 2, 0)
        val expectedTime = movieDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

        val dto = TicketDTO(cinema, hall, seatRow, seatColumn, movieTitle, movieDateTime)

        //Creating a ticket
        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        val updatedRow = 2
        val updatedColumn = 3

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", id)
                .param("seatRow", updatedRow)
                .param("seatColumn", updatedColumn)
                .patch("/{ticketId}/seat")
                .then()
                .statusCode(204)

        //Check if updated correctly
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", id)
                .get("/{ticketId}")
                .then()
                .statusCode(200)
                .body("ticketId", CoreMatchers.equalTo(id))
                .body("cinema", CoreMatchers.equalTo(cinema))
                .body("seatRow", CoreMatchers.equalTo(updatedRow))
                .body("seatColumn", CoreMatchers.equalTo(updatedColumn))
                .body("movieTitle", CoreMatchers.equalTo(movieTitle))
                .body("movieDateTime", CoreMatchers.equalTo(expectedTime))
    }
}