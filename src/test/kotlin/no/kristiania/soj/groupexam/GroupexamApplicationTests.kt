package no.kristiania.soj.groupexam

import no.kristiania.soj.groupexam.ticket.util.WrappedResponse.ResponseStatus.ERROR
import no.kristiania.soj.groupexam.ticket.util.WrappedResponse.ResponseStatus.SUCCESS

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(GroupexamApplication::class)],
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroupexamApplicationTests {

	@LocalServerPort
	protected var port = 0


	@Before
	fun initTest() {

		// RestAssured configs shared by all the tests
		RestAssured.baseURI = "http://localhost"
		RestAssured.port = port
		RestAssured.basePath = "/api"
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
	}

	@Test
	fun testCorrectInput() {

		val x = 1

		RestAssured.given().accept(ContentType.JSON)
				.param("x", x)
				.get("/test")
				.then()
				.statusCode(200)
				.body("code", Matchers.equalTo(200))
				.body("status", Matchers.equalToIgnoringCase(SUCCESS.toString()))
				.body("data.result", Matchers.equalTo(null))
				.body("message", Matchers.equalTo("Correct value"))
	}

	@Test
	fun testWrongInput() {

		val x = -1

		RestAssured.given().accept(ContentType.JSON)
				.param("x", x)
				.get("/test")
				.then()
				.statusCode(400)
				.body("code", Matchers.equalTo(400))
				.body("status", Matchers.equalToIgnoringCase(ERROR.toString()))
				.body("data.result", Matchers.equalTo(null))
				.body("message", Matchers.equalTo("Needs to be 1"))
	}
}
