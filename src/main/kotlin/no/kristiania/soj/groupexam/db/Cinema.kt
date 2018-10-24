package no.kristiania.soj.groupexam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
class Cinema(
        @get:NotBlank
        @get:Size(max = 128)
        var name: String,

        @get:NotBlank
        @get:Size(max = 256)
        var movies: List<Movie>,

        @get:NotBlank
        @get:Size(max = 256)
        var halls: String,

        @get:NotBlank
        @get:GeneratedValue
        var id: Long? = null
)