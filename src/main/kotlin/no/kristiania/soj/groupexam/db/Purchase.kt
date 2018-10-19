package no.kristiania.soj.groupexam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Purchase (

        //Name of the cinema it's playing at
        @get:NotBlank
        @get:Size(max = 64)
        var cinemaName: String,

        //maybe a map to where it's located?
        @get:NotBlank
        @get:Size(max = 256)
        var geoLocation: String,

        //Where inside the cinema is it playing? Hall 1? Hall 3?
        @get:NotBlank
        @get:Size(max = 64)
        var cinemaHall: String,


        @get:NotBlank
        var seats: Int,

        @get:Max(2200)
        @get:NotNull
        var price: Int,

        @get:Id
        @get:GeneratedValue
        var id: Long? = null
)