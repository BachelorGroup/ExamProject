package no.kristiania.soj.groupexam.movie.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class MovieDTO(

        @ApiModelProperty("Title of movie")
        @get:NotBlank
        var title: String? = null,

        @ApiModelProperty("Short description of what the movie is about")
        @get:NotBlank
        var description: String? = null,

        @ApiModelProperty("Actors, genre and so on")
        @get:NotBlank
        var info: String? = null,

        @ApiModelProperty("Director of the movie")
        @get:NotBlank
        var director: String? = null,

        @ApiModelProperty("Rating of the movie, out of 100")
        @get:NotNull
        var rating: Int? = null,

        @ApiModelProperty("Release date of movie")
        @get:NotBlank
        var releaseDate: String? = null,

        @ApiModelProperty("The idgit  of the movie")
        var id: String? = null
)