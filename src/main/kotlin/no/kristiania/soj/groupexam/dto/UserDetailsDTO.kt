/*package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import no.kristiania.soj.groupexam.db.TicketEntity
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserDetailsDTO(

        @ApiModelProperty("Name of user")
        @get:NotBlank
        var name: String? = null,

        @ApiModelProperty("Last name of user")
        @get:NotBlank
        var lastName: String? = null,

        @ApiModelProperty("Email of user")
        @get:NotBlank
        var email: String? = null,

        @ApiModelProperty("Age of user")
        @get:NotNull
        var age: Int? = null,

        @ApiModelProperty("Age of user")
        @get:NotNull
        var purchasedTickets: List<TicketEntity>? = null,

        @ApiModelProperty("Username of user")
        @get:NotBlank
        var username: String? = null
)*/