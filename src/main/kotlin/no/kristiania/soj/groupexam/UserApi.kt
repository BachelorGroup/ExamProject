package no.kristiania.soj.groupexam

import com.google.common.base.Throwables
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.kristiania.soj.groupexam.db.UserRepository
import no.kristiania.soj.groupexam.dto.UserConverter
import no.kristiania.soj.groupexam.dto.UserDTO
import no.kristiania.soj.groupexam.util.RestResponseFactory
import no.kristiania.soj.groupexam.util.WrappedResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.ConstraintViolationException

@RestController
@RequestMapping(path = ["/api/users"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@Validated
class UserApi {

    @Autowired
    private lateinit var userCrud: UserRepository

    //later will add userDetailsCrud

    @ApiOperation("Simple user creation")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ApiResponse(code = 201, message = "The username of the created user")
    fun createUser(
            @ApiParam("Username and password. Do not specify role")
            @RequestBody
            dto: UserDTO

    ): ResponseEntity<WrappedResponse<Void>> {

        if (!dto.enabled!!) {
            return RestResponseFactory.userFailure("User has to be enabled")
        }

        if (dto.username == null || dto.password == null) {
            return RestResponseFactory.userFailure("Username or password is missing")
        }

        try {
            userCrud.createUser(dto.username!!, dto.password!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return RestResponseFactory.userFailure("Constraint violation")
            }
            throw e
        }

        return RestResponseFactory.created(URI.create("/api/users/" + dto.username))
    }

    @ApiOperation("Get all users")
    @GetMapping
    fun getAll(

    ): ResponseEntity<WrappedResponse<List<UserDTO>>> {

        return ResponseEntity.status(200).body(
                WrappedResponse(
                        code = 200,
                        data = UserConverter.transform(userCrud.findAll()),
                        message = "Fetched all users"
                ).validated()
        )
    }

    @ApiOperation("Get a single user specified by username")
    @GetMapping(path = ["/{username}"])
    fun getByUsername(@ApiParam("The username of the user")
                @PathVariable("username")
                pathId: String?)
            : ResponseEntity<WrappedResponse<UserDTO>> {

        val user = userCrud.findById(pathId).orElse(null)
                ?: return RestResponseFactory.notFound(
                "The requested user with username '$pathId' is not in the database")

        return RestResponseFactory.payload(200, UserConverter.transform(user))
    }

    //private?
    @ApiOperation("Delete a user with the given username")
    @DeleteMapping(path = ["/{username}"])
    fun deleteByUsername(@ApiParam("The username of the user")
               @PathVariable("username")
               pathId: String?): ResponseEntity<WrappedResponse<Void>> {

        if (!userCrud.existsById(pathId)) {
            return RestResponseFactory.notFound(
                    "The requested user with username '$pathId' is not in the database")
        }

        userCrud.deleteById(pathId)
        return RestResponseFactory.noPayload(204)
    }

    @ApiOperation("Update a specific user")
    @PutMapping(path = ["/{username}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun updateByUsername(
            @ApiParam("The username of the user")
            @PathVariable("username")
            pathId: String,
            //
            @ApiParam("New data for updating the user")
            @RequestBody
            dto: UserDTO
    ): ResponseEntity<WrappedResponse<Void>> {

        if(dto.username == null){
            return RestResponseFactory.userFailure("Missing username JSON payload")
        }

        if(dto.username != pathId){
            return RestResponseFactory.userFailure("Inconsistent username between URL and JSON payload", 409)
        }

        val entity = userCrud.findById(pathId).orElse(null)
                ?: return RestResponseFactory.notFound(
                "The requested user with username '$pathId' is not in the database. " +
                        "This PUT operation will not create it.")

        entity.password = dto.password!!

        userCrud.save(entity)

        return RestResponseFactory.noPayload(204)
    }
}
