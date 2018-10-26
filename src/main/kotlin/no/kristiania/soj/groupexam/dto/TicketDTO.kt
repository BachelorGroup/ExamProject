package no.kristiania.soj.groupexam.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class TicketDTO (

        @ApiModelProperty("Name of the cinema it's playing at, f.ex Colosseum in Oslo, Norway")
        @get:NotBlank
        var cinema: String? = null,

        @ApiModelProperty("There are several halls in a cinema, this describes which")
        @get:NotNull
        var hall: Int? = null,

        @ApiModelProperty("The row the ticket is assigned to")
        @get:NotNull
        var seatRow: Int? = null,

        @ApiModelProperty("The column the ticket is assigned to")
        @get:NotNull
        var seatColumn: Int? = null,

        @ApiModelProperty("Name of the movie")
        @get:NotBlank
        var movieTitle: String? = null,

        @ApiModelProperty("What time the ticket is for")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        @get:NotBlank
        var movieDateTime: LocalDateTime? = null,

        @ApiModelProperty("What time the ticket was purchased")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        @get:NotBlank
        var purchaseDateTime: LocalDateTime? = null,

        @ApiModelProperty("The ticketId of the ticket")
        var ticketId: String? = null
)