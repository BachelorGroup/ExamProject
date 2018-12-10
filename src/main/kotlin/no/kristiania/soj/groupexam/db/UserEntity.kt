package no.kristiania.soj.groupexam.db

import javax.persistence.ElementCollection
import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.Id
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

        @get:ElementCollection
        var roles: MutableSet<String>,

        var enabled: Boolean
)
