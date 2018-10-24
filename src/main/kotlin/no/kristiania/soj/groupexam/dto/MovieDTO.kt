package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

class MovieDTO(

        @ApiModelProperty("Title of movie")
        @get:NotNull
        var title: String? =null,

        @ApiModelProperty("Short description of what the movie is about")
        @get:NotNull
        var description: String? = null,

        @ApiModelProperty("Directors, actors, genre and so on")
        @get:NotNull
        var info: String? = null,

        @ApiModelProperty("Rating of the movie, out of 100")
        @get:NotNull
        var rating: Int? = null,

        @ApiModelProperty("Release date of movie")
        @get:NotNull
        var releaseDate: String? = null,

        @ApiModelProperty("The ticketId of the movie")
        var id: String? = null
)