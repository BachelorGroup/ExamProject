package no.kristiania.soj.groupexam.movie.db

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class Movie(
        @get:NotBlank
        var title: String,

        @get:NotBlank
        var director: String,

        @get:NotBlank
        var description: String,

        @get:NotBlank
        var info: String,

        @get:NotNull
        var rating: Int,

        @get:NotNull
        var releaseDate: ZonedDateTime,

        @get:Id
        @get:GeneratedValue
        var id: Long? = null
)