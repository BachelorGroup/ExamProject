package no.kristiania.soj.groupexam

import io.restassured.RestAssured
import org.springframework.stereotype.Service

@Service
class TestUtil{

    fun initializeTest(port: Int, path: String) {

        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = path
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }
}