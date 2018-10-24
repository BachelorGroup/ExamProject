package no.kristiania.soj.groupexam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Ticket (

        @get:Id
        @get:GeneratedValue
        var id: Long? = null,

        //Name of the cinema it's playing at
        @get:NotBlank
        @get:Size(max = 64)
        var cinema: String,

        //Where inside the cinema is it playing? Hall 1? Hall 3?
        @get:NotBlank
        @get:Size(max = 64)
        var hall: String,

        @get:NotBlank
        var seat: Int,

        @get:Max(2200)
        @get:NotNull
        var price: Int
)