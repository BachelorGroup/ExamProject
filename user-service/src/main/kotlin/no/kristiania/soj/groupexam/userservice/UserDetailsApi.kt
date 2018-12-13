package no.kristiania.soj.groupexam.userservice

import no.kristiania.soj.groupexam.userservice.db.UserDetailsEntity
import no.kristiania.soj.groupexam.userservice.db.UserDetailsRepository
import no.kristiania.soj.groupexam.userservice.dto.UserDetailsConverter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import no.kristiania.soj.groupexam.userservice.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
class UserDetailsApi{

    @Autowired
    private lateinit var crud: UserDetailsRepository

    /**
     * Get the number of existing users
     */
    @GetMapping(path = ["/userDetailsCount"],
            produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    fun getCount(): ResponseEntity<Long> {

        return ResponseEntity.ok(crud.count())
    }

    /*
        Note: for simplicity here using Entity as DTO...
     */

    @GetMapping(path = ["/userDetails"],
            produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    fun getAll(): ResponseEntity<List<UserDetailsDTO>> {

        return ResponseEntity.ok(UserDetailsConverter.transform(crud.findAll()))
    }


    @GetMapping(path = ["/userDetails/{id}"],
            produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    fun getById(@PathVariable id: String)
            : ResponseEntity<UserDetailsDTO> {

        val entity = crud.findById(id).orElse(null)
                ?: return ResponseEntity.status(404).build()

        return ResponseEntity.ok(UserDetailsConverter.transform(entity))
    }



    @PutMapping(path = ["/userDetails/{id}"],
            consumes = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    fun replace(
            @PathVariable id: String,
            @RequestBody dto: UserDetailsDTO)
            : ResponseEntity<Void> {

        if (id != dto.username) {
            return ResponseEntity.status(409).build()
        }

        val alreadyExists = crud.existsById(id)
        var code = if(alreadyExists) 204 else 201

        val entity = UserDetailsEntity(dto.username, dto.name, dto.surname, dto.email, dto.age, dto.purchasedTickets)

        try {
            crud.save(entity)
        } catch (e: Exception) {
            code = 400
        }

        return ResponseEntity.status(code).build()
    }
}

