package no.kristiania.soj.groupexam.movie.dto

import io.swagger.annotations.ApiModelProperty
import java.time.ZonedDateTime

data class MovieDTO(
        @ApiModelProperty("ID of the movie")
        var id: String? = null,

        @ApiModelProperty("Title of the movie")
        var title: String? = null,

        @ApiModelProperty("Director of the movie")
        var director: String? = null,

        @ApiModelProperty("Information about the the story of the movie")
        var description: String? = null,

        @ApiModelProperty("Information, such as actors and locations")
        var info: String? = null,

        @ApiModelProperty("Rating of the movie, out of 100")
        var rating: Int? = null,

        @ApiModelProperty("The release date of the movie")
        var releaseDate: ZonedDateTime? = null
)