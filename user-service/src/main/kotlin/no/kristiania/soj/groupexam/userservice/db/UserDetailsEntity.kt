package no.kristiania.soj.groupexam.userservice.db

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class UserDetailsEntity(

        @get:Id
        @get:NotBlank
        @get:Size(max = 32)
        var username: String?,

        //@get:Size(max=64)
        @get:NotBlank
        var name: String?,

        @get:NotBlank
        var surname: String?,

        @get:NotBlank
        @get:Email
        var email: String?,

        @get:NotNull
        var age: Int?,

        @get:ElementCollection
        var purchasedTickets: MutableList<Long>? = mutableListOf()


)