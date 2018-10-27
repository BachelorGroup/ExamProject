package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
//import no.kristiania.soj.groupexam.db.UserDetailsEntity
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserDTO(

        @ApiModelProperty("Username of user")
        @get:NotBlank
        var username: String? = null,

        @ApiModelProperty("Password of user")
        @get:NotBlank
        var password: String? = null,

        //Default roles is to add 'USER'
        @ApiModelProperty("Role of user")
        @get:NotNull
        var roles: MutableSet<String> = Collections.singleton("USER"),

        @ApiModelProperty("If user is enabled")
        @get:NotNull
        var enabled: Boolean? = null
/*
        @ApiModelProperty("The details of a user; name, age etc.")
        @get:NotNull
        var details: UserDetailsEntity? = null*/
)