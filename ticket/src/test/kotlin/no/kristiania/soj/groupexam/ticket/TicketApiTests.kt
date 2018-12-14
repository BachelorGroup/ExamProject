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
@SpringBootTest(classes = [(TicketApplication::class)],
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

    private fun createTicket(): TicketDTO {

        val cinema = "Colosseum"
        val hall = 1
        val seatRow = 5
        val seatColumn = 7
        val movieTitle = "Monsters INC"
        val movieDateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 18, 2, 0)

        return TicketDTO(cinema, hall, seatRow, seatColumn, movieTitle, movieDateTime)
    }

    @Test
    fun testCreateAndGet() {

        val dto = createTicket()
        val expectedTime = dto.movieDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

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
                .body("cinema", CoreMatchers.equalTo(dto.cinema))
                .body("seatRow", CoreMatchers.equalTo(dto.seatRow))
                .body("seatColumn", CoreMatchers.equalTo(dto.seatColumn))
                .body("movieTitle", CoreMatchers.equalTo(dto.movieTitle))
                .body("movieDateTime", CoreMatchers.equalTo(expectedTime))
    }

    @Test
    fun testInvalidPostId() {

        val dto = createTicket()
        dto.ticketId = "1"

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(400)
    }

    @Test
    fun testNullPost() {

        val dto = createTicket()

        dto.cinema = null

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(400)
    }

    @Test
    fun testInvalidGetId() {

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", "Invalid")
                .get("/{ticketId}")
                .then()
                .statusCode(404)
    }

    @Test
    fun testNonExistingGetId() {

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", 1)
                .get("/{ticketId}")
                .then()
                .statusCode(404)
    }

    @Test
    fun testUpdateTicket() {

        val dto = createTicket()
        val expectedTime = dto.movieDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

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
                        cinema = dto.cinema,
                        hall = dto.hall,
                        seatRow = dto.seatRow,
                        seatColumn = dto.seatColumn,
                        movieTitle = updatedMovieTitle,
                        movieDateTime = dto.movieDateTime,
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
                .body("cinema", CoreMatchers.equalTo(dto.cinema))
                .body("seatRow", CoreMatchers.equalTo(dto.seatRow))
                .body("seatColumn", CoreMatchers.equalTo(dto.seatColumn))
                .body("movieTitle", CoreMatchers.equalTo(updatedMovieTitle))
                .body("movieDateTime", CoreMatchers.equalTo(expectedTime))
    }

    @Test
    fun testInvalidPutId() {

        val dto = createTicket()

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", 1)
                .body(TicketDTO(
                        cinema = dto.cinema,
                        hall = dto.hall,
                        seatRow = dto.seatRow,
                        seatColumn = dto.seatColumn,
                        movieTitle = dto.movieTitle,
                        movieDateTime = dto.movieDateTime,
                        ticketId = null))
                .put("/{ticketId}")
                .then()
                .statusCode(404)
    }

    @Test
    fun testMismatchPutId() {

        val dto = createTicket()

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", 1)
                .body(TicketDTO(
                        cinema = dto.cinema,
                        hall = dto.hall,
                        seatRow = dto.seatRow,
                        seatColumn = dto.seatColumn,
                        movieTitle = dto.movieTitle,
                        movieDateTime = dto.movieDateTime,
                        ticketId = "2"))
                .put("/{ticketId}")
                .then()
                .statusCode(409)
    }

    @Test
    fun testNonExistingPutId() {

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        val dto = createTicket()

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", 1)
                .body(TicketDTO(
                        cinema = dto.cinema,
                        hall = dto.hall,
                        seatRow = dto.seatRow,
                        seatColumn = dto.seatColumn,
                        movieTitle = dto.movieTitle,
                        movieDateTime = dto.movieDateTime,
                        ticketId = "1"))
                .put("/{ticketId}")
                .then()
                .statusCode(404)
    }

    @Test
    fun testNullPut() {

        val dto = createTicket()

        val id = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)
                .extract().asString()

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", id)
                .body(TicketDTO(
                        cinema = null,
                        hall = null,
                        seatRow = null,
                        seatColumn = null,
                        movieTitle = null,
                        movieDateTime = null,
                        ticketId = id))
                .put("/{ticketId}")
                .then()
                .statusCode(400)
    }

    @Test
    fun testUpdateSeat() {

        val dto = createTicket()
        val expectedTime = dto.movieDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))


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
                .body("cinema", CoreMatchers.equalTo(dto.cinema))
                .body("seatRow", CoreMatchers.equalTo(updatedRow))
                .body("seatColumn", CoreMatchers.equalTo(updatedColumn))
                .body("movieTitle", CoreMatchers.equalTo(dto.movieTitle))
                .body("movieDateTime", CoreMatchers.equalTo(expectedTime))
    }

    @Test
    fun testInvalidPatchId() {

        val updatedRow = 2
        val updatedColumn = 3

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", "random")
                .param("seatRow", updatedRow)
                .param("seatColumn", updatedColumn)
                .patch("/{ticketId}/seat")
                .then()
                .statusCode(400)
    }

    @Test
    fun testNonExistingPatchId() {

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        val updatedRow = 2
        val updatedColumn = 3

        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("ticketId", 1)
                .param("seatRow", updatedRow)
                .param("seatColumn", updatedColumn)
                .patch("/{ticketId}/seat")
                .then()
                .statusCode(404)
    }

    @Test
    fun testDeleteTicket() {

        val dto = createTicket()

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
                .delete("/${id}")
                .then()
                .statusCode(204)

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))
    }

    @Test
    fun testInvalidDeleteId() {

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .delete("Invalid")
                .then()
                .statusCode(400)
    }

    @Test
    fun testNonExistingDeleteId() {

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("size()", CoreMatchers.equalTo(0))

        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .delete("1")
                .then()
                .statusCode(404)
    }
}