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
    fun testNotFoundUser() {

        RestAssured.given().accept(ContentType.JSON)
                .get("/notAusername")
                .then()
                .statusCode(404)
                .body("code", CoreMatchers.equalTo(404))
                .body("message", CoreMatchers.not(CoreMatchers.equalTo(null)))
    }

    @Test
    fun testRetrieveEachSingleUser() {

        val users = RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(3))
                .extract().body().jsonPath().getList("data", UserDTO::class.java)

        for (u in users) {

            RestAssured.given().accept(ContentType.JSON)
                    .get("/${u.username}")
                    .then()
                    .statusCode(200)
                    .body("data.username", CoreMatchers.equalTo(u.username))
                    //remember we need to change to hashed password and
                    //use matched when we integrate postgres
                    .body("data.password", CoreMatchers.equalTo(u.password))
                    .body("data.enabled", CoreMatchers.equalTo(u.enabled))
                    .body("data.roles[0]", CoreMatchers.equalTo("USER"))
        }
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
                .body("data.roles[0]", CoreMatchers.equalTo("USER"))
    }

    @Test
    fun testDeleteAllUsers() {

        val users = RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(3))
                .extract().body().jsonPath().getList("data", UserDTO::class.java)

        for (u in users) {

            RestAssured.given().accept(ContentType.JSON)
                    .delete("/${u.username}")
                    .then()
                    .statusCode(204)
        }

        RestAssured.given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .body("data.size()", CoreMatchers.equalTo(0))
    }

    @Test
    fun testNotAuthenticated() {

        testUtil.initializeTest(port, "/api")

        RestAssured.given().get("/testUser")
                .then()
                .statusCode(401)
                .header("WWW-Authenticate", CoreMatchers.containsString("Basic realm=\"Realm\""))
    }

    @Test
    fun testNotAuthorizedUser() {

        testUtil.initializeTest(port, "/api")

        RestAssured.given()
                .auth().basic("foo", "bar123")
                .get("/testAdmin")
                .then()
                .statusCode(403)
    }

    @Test
    fun testAuthorizedUser() {

        testUtil.initializeTest(port, "/api")

        //as we are currently just using the in memory auth from httpbasic
        //we have to use one of those 'users' and not the ones created in this test
        RestAssured.given()
                .auth().basic("foo", "bar123")
                .get("/testUser")
                .then()
                .statusCode(200)
    }

    @Test
    fun testAuthenticatedAdmin() {

        testUtil.initializeTest(port, "/api")

        //as we are currently just using the in memory auth from httpbasic
        //we have to use one of those 'users' and not the ones created in this test
        RestAssured.given()
                .auth().basic("admin", "admin")
                .get("/testAdmin")
                .then()
                .statusCode(200)
    }
}