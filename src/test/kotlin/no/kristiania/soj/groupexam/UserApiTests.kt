package no.kristiania.soj.groupexam

import io.restassured.RestAssured
import io.restassured.http.ContentType
import no.kristiania.soj.groupexam.db.UserRepository
import no.kristiania.soj.groupexam.dto.UserDTO
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(GroupexamApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiTests {

    @LocalServerPort
    protected var port = 0
    private var path = "/api/users"

    @Autowired
    private lateinit var testUtil: TestUtil

    @Autowired
    private lateinit var repository: UserRepository
/*
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
*/
    @Before
    @After
    fun clean() {

        testUtil.initializeTest(port, path)

        repository.run{
            deleteAll()
            createUser("foo", "bar")
            createUser("bar", "foo")
            createUser("user", "1234")
        }
    }

    @Test
    fun getAll() {

        RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(3))
    }

    @Test
    fun testCreateAndGet() {

        val username = "foop"
        val password = "bart"

        val dto = UserDTO(username = username, password = password, enabled = true)

        //Should have 3 users
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(3))

        //Creating a user
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dto)
                .post()
                .then()
                .statusCode(201)

        /*
            later we have to extract the hashed password from the get to compare it to the non-crypted
            password. Will do this when we setup postgres
          */
        //Should be 4 user now
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(4))


        //One ticket with same data as the POST
        RestAssured.given().accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .pathParam("username", username)
                .get("/{username}")
                .then()
                .statusCode(200)
                .body("data.username", CoreMatchers.equalTo(username))
                //.body("password", CoreMatchers.equalTo(passwordEncoder.matches(password, hashedPassword)))
                .body("data.password", CoreMatchers.equalTo(password))
                .body("data.enabled", CoreMatchers.equalTo(true))
    }
}
