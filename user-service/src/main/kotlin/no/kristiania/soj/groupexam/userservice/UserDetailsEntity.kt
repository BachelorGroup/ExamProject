package no.kristiania.soj.groupexam.userservice

import java.time.LocalDateTime
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
        var age: Int?

        /*
            userOwner is a manyToOne field in TicketEntity which is
            the username of the owner of the ticket

            fetch type is default lazy
        */
        /*
        @get:NotBlank
        @get:OneToMany(mappedBy = "userOwner")
        var purchasedTickets: List<TicketEntity>

        ????
*/


)