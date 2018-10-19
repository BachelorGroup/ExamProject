package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

class UserDTO(

        @ApiModelProperty("Username of user")
        @get:NotNull
        var username: String? =null,

        @ApiModelProperty("password of user")
        @get:NotNull
        var password: String? = null,

        @ApiModelProperty("Email of user")
        @get:NotNull
        var email: String? = null,

        @ApiModelProperty("The id of the user")
        var id: String? = null
)