package no.kristiania.soj.groupexam

import com.google.common.base.Throwables
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.kristiania.soj.groupexam.db.UserRepository
import no.kristiania.soj.groupexam.dto.UserConverter
import no.kristiania.soj.groupexam.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun createTicket(
            @ApiParam("Username and password. Do not specify role or enabled")
            @RequestBody
            dto: UserDTO

    ): ResponseEntity<String> {

        if (!(dto.roles!!.isEmpty())) {
            return ResponseEntity.status(400).build()
        }

        if (!dto.enabled) {
            return ResponseEntity.status(400).build()
        }

        if (dto.username == null || dto.password == null) {
            return ResponseEntity.status(400).build()
        }

        try {
            userCrud.createUser(dto.username!!, dto.password!!)
        } catch (e: Exception) {
            if(Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(201).body(dto.username)
    }

    @ApiOperation("Get all users")
    @GetMapping(path = ["/"])
    fun get(

    ): ResponseEntity<List<UserDTO>> {

        val list = userCrud.findAll()

        return ResponseEntity.ok(UserConverter.transform(list))
    }

    @ApiOperation("Get a single user specified by username")
    @GetMapping(path = ["/{username}"])
    fun getNews(@ApiParam("The username of the user")
                @PathVariable("username")
                pathId: String?)
            : ResponseEntity<UserDTO> {

        val dto = userCrud.findById(pathId).orElse(null) ?: return ResponseEntity.status(404).build()
        return ResponseEntity.ok(UserConverter.transform(dto))
    }

    //private?
    @ApiOperation("Delete a user with the given username")
    @DeleteMapping(path = ["/{username}"])
    fun delete(@ApiParam("The username of the user")
               @PathVariable("username")
               pathId: String?): ResponseEntity<Any> {

        if (!userCrud.existsById(pathId)) {
            return ResponseEntity.status(404).build()
        }

        userCrud.deleteById(pathId)
        return ResponseEntity.status(204).build()
    }
}
