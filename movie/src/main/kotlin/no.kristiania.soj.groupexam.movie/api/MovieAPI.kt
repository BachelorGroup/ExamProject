package no.kristiania.soj.groupexam.movie.api

import com.google.common.base.Throwables
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.kristiania.soj.groupexam.movie.db.Movie
import no.kristiania.soj.groupexam.movie.db.MovieRepository
import no.kristiania.soj.groupexam.movie.dto.MovieConverter
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolationException

@RestController
@RequestMapping(path = ["/api/movie"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@Validated
class MovieAPI {
    @Autowired
    private lateinit var crud: MovieRepository

    @ApiOperation("Create movie")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ApiResponse(code = 201, message = "Movie is presisted")
    fun addMovie(@ApiParam("All info about movies that are created")
                 @RequestBody
                 DTO: MovieDTO): ResponseEntity<Long> {
        if (!(DTO.id.isNullOrEmpty())) {
            return ResponseEntity.status(400).build()
        }
        if (DTO.title == null ||
                DTO.director == null ||
                DTO.info == null ||
                DTO.description == null ||
                DTO.rating == null ||
                DTO.releaseDate == null) {
            return ResponseEntity.status(400).build()
        }
        val id: Long?
        try {
            id = crud.addMovie(DTO.title!!,
                    DTO.director!!,
                    DTO.info!!,
                    DTO.description!!,
                    DTO.rating!!,
                    DTO.releaseDate!!)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(201).body(id)
    }
    @ApiOperation("Get movies")
    @GetMapping
    fun getMovies () : ResponseEntity<List<MovieDTO>> {
        val movieList = crud.findAll()
        return ResponseEntity.ok(MovieConverter.transform(movieList))
    }
}