package no.kristiania.soj.groupexam.movie.api

import com.google.common.base.Throwables
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.kristiania.soj.groupexam.movie.db.MovieRepository
import no.kristiania.soj.groupexam.movie.dto.MovieConverter
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException

const val ID_PARAM = "The id of the movie"
const val BASE_JSON = "application/json;charset=UTF-8"

const val MOVIE_JSON = "application/movies+json;charset=utf-8;version=1"

@Api(value = "/movies", description = "Movie tickets and info")
@RequestMapping(path = ["/movies"], produces = [MOVIE_JSON, BASE_JSON])
@RestController
class GroupexamApi {

    @Autowired
    private lateinit var crud: MovieRepository

    @ApiOperation("Get list of all movies")
    @GetMapping
    fun get(@ApiParam("Title of the movie")
            @RequestParam("movie", required = true)
            movie: String?,
            @ApiParam("The director of the movie")
            @RequestParam("director", required = true)
            director: String?,
            @ApiParam("The description of the movie")
            @RequestParam("description", required = true)
            description: String?,
            @ApiParam("The info for the movie, such as actors and genre")
            @RequestParam("info", required = true)
            info: String?,
            @ApiParam("The rating of the movie, in Int form")
            @RequestParam("rating", required = true)
            rating: Int?,
            @ApiParam("The date of the movie, when the movie came out")
            @RequestParam("releaseDate", required = true)
            releaseDate: String?,
            @ApiParam("The id for the movie")
            @RequestParam("id", required = true)
            id: Long?
    ): ResponseEntity<List<MovieDTO>> {
        val list = if (movie.isNullOrBlank() && director.isNullOrBlank()) {
            crud.findAll()
        } else if (!movie.isNullOrBlank() && !director.isNullOrBlank()) {
            crud.findAllByMovieAndDirector(movie, director)
        } else if (!movie.isNullOrBlank()) {
            crud.findAllByMovie(movie)
        } else if (!releaseDate.isNullOrBlank()) {
            crud.findAllByDate(releaseDate)
        } else if (!rating.toString().isEmpty()) {
            crud.findAllByRating(rating!!)
        } else if (!id.toString().isEmpty()) {
            crud.findAllById(id!!)
        } else {
            crud.findAllByDirector(director!!)
        }
        return ResponseEntity.ok(MovieConverter.transform(list))
    }

    @ApiOperation("Add a movie")
    @PostMapping(consumes = [MOVIE_JSON, BASE_JSON])
    @ApiResponse(code = 201, message = "ID for the movie added")
    fun addMovie(@ApiParam("All info for a movie, title, director and release date")
                 @RequestBody
                 dto: MovieDTO): ResponseEntity<Long> {
        if (!(dto.id.isNullOrEmpty() && dto.title.isNullOrBlank())) {
            return ResponseEntity.status(400).build()
        }
        if (dto.director == null ||
                dto.description == null ||
                dto.info == null ||
                dto.rating == null ||
                dto.releaseDate == null) {
            return ResponseEntity.status(400).build()
        }
        val id: Long?

        try {
            id = crud.addMovie(dto.title!!, dto.director!!, dto.rating!!, dto.description!!, dto.info!!, dto.releaseDate!!)
        } catch (e: Exception) {
            if (Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }
        return ResponseEntity.status(201).body(id)
    }
}