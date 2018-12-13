package no.kristiania.soj.groupexam.movie

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class MovieEntity(
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
        var releaseDate: LocalDateTime,

        @get:Id
        @get:GeneratedValue
        var id: Long? = null
)