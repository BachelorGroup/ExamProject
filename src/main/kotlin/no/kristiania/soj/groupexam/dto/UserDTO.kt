package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import java.util.*
import javax.validation.constraints.NotNull

class UserDTO(

        @ApiModelProperty("Username of user")
        @get:NotNull
        var username: String? =null,

        @ApiModelProperty("Password of user")
        @get:NotNull
        var password: String? = null,

        //Default roles is to add 'USER'
        @ApiModelProperty("Role of user")
        @get:NotNull
        var roles: MutableSet<String> = Collections.singleton("USER"),

        @ApiModelProperty("If user is enabled")
        @get:NotNull
        var enabled: Boolean? = null
)