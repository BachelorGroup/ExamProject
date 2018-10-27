package no.kristiania.soj.groupexam.db

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class UserEntity(

        @get:Id
        @get:NotBlank
        @get:Size(max = 32)
        var username: String,

        @get:NotBlank
        @get:Size(max = 32)
        var password: String,

        @get:ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableSet<String>,

        var enabled: Boolean

        /*
            OneToOne relationship that assumes the Id of the source and target are the same
            In this case the Id refers to the username of the user

            We want to load the details of the user EAGERly
        *//*
        @get:OneToOne(fetch = FetchType.EAGER)
        @get:MapsId
        var details: UserDetailsEntity*/
)
