package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

class UserDTO(

        @ApiModelProperty("Username of user")
        @get:NotNull
        var username: String? =null,

        @ApiModelProperty("Password of user")
        @get:NotNull
        var password: String? = null,

        @ApiModelProperty("Role of user")
        @get:NotNull
        var roles: MutableSet<String>? = null,

        @ApiModelProperty("If user is enabled")
        @get:NotNull
        var enabled: Boolean = true,

        @ApiModelProperty("The id of the user")
        var id: String? = null
)