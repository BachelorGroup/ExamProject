package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

class PurchaseDTO(

        @ApiModelProperty("Name of the cinema it's playing at, f.ex Colosseum in Oslo, Norway")
        @get:NotNull
        var cinemaName: String? = null,

        @ApiModelProperty("Possible map for the user to see where in the world the cinema is")
        @get:NotNull
        var geoLocation: String? = null,

        @ApiModelProperty("There are several halls in a cinema, this describes which")
        @get:NotNull
        var cinemaHall: String? = null,

        @ApiModelProperty("Seats that are taken or avalible")
        @get:NotNull
        var seats: Int? = null,

        @ApiModelProperty("Price of a ticket")
        @get:NotNull
        var price: Int? = null,

        @ApiModelProperty("The id of the purchase")
        var id: String? = null
)