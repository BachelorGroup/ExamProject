package no.kristiania.soj.groupexam.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import no.kristiania.soj.groupexam.db.MovieRepository
import no.kristiania.soj.groupexam.dto.MovieConverter
import no.kristiania.soj.groupexam.dto.MovieDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

const val ID_PARAM = "The numeric id of the news"
const val BASE_JSON = "application/json;charset=UTF-8"

const val MOVIE_JSON = "application/movies+json;charset=utf-8;version=1"

@Api(value= "/movies", description = "Movie tickets and info")
@RequestMapping(path =  ["/movies"], produces = [MOVIE_JSON, BASE_JSON])
@RestController
class GroupexamApi {

    @Autowired
    private lateinit var crud: MovieRepository

    //    @Value("\${server.servlet.context-path}")
//    private lateint var contextPath : String
    @ApiOperation("Get list of all movies")
    @GetMapping
    fun get(@ApiParam("Title of the movie")
            @RequestParam("movie", required = true)
            movie: String?,
            @ApiParam("The director of the movie")
            @RequestParam("director", required = true)
            director: String?): ResponseEntity<List<MovieDTO>> {
        val list = if (movie.isNullOrBlank() && director.isNullOrBlank()) {
            crud.findAll()
        } else if (!movie.isNullOrBlank() && !director.isNullOrBlank()) {
            crud.findAllByMovieAndDirector(movie!!, director!!)
        } else if (!movie.isNullOrBlank()) {
            crud.findAllByMovie(movie!!)
        } else {
            crud.findAllByDirector(director!!)
        }
        return ResponseEntity.ok(MovieConverter.transform(list))
    }
}