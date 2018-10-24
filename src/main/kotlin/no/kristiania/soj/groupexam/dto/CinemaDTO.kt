package no.kristiania.soj.groupexam.dto

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

class CinemaDTO(

        @ApiModelProperty("Name of the cinema")
        @get:NotNull
        var name: String? = null,

        @ApiModelProperty("List of movies playing")
        @get:NotNull
        var movies: List<no.kristiania.soj.groupexam.db.Movie>? = null,

        @ApiModelProperty("The different halls a cinema has")
        @get:NotNull
        var halls: String? = null,

        @ApiModelProperty("The id of the cinema, for code purpose")
        var id: Long? = null
)