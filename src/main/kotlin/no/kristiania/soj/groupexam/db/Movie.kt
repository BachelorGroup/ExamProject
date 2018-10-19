package no.kristiania.soj.groupexam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class Movie (

        @get:NotBlank
        @get:Size(max = 64)
        var title: String,

        @get:NotBlank
        @get:Size(max = 256)
        var description: String,

        @get:NotBlank
        @get:Size(max = 256)
        var info: String,

        @get:NotBlank
        var rating: Int,

        @get:Max(2200) @get:NotNull
        var releaseDate: String,

        @get:Id
        @get:GeneratedValue
        var id: Long? = null
)