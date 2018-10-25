package no.kristiania.soj.groupexam.db

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class TicketEntity (

        @get:NotBlank
        var cinema: String,

        @get:NotNull
        var hall: Int,

        @get:NotNull
        var seatRow: Int,

        @get:NotNull
        var seatColumn: Int,

        @get:NotBlank
        var movieTitle: String,

        @get:NotNull
        var movieDateTime: LocalDateTime,

        @get:NotNull
        var purchaseDateTime: LocalDateTime,

        @get:Id
        @get:GeneratedValue
        var id: Long? = null
)