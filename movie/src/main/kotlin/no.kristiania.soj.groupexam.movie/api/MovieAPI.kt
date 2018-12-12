package no.kristiania.soj.groupexam.movie.api

import com.google.common.base.Throwables
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
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
@RequestMapping(path = ["/api/movies"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
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
            id = crud.addMovie(
                    DTO.title!!,
                    DTO.director!!,
                    DTO.description!!,
                    DTO.info!!,
                    DTO.rating!!,
                    DTO.releaseDate!!
            )
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
    fun getMovies(): ResponseEntity<List<MovieDTO>> {
        val movieList = crud.findAll()
        return ResponseEntity.ok(MovieConverter.transform(movieList))
    }

    @ApiOperation("Get movie by ID")
    @GetMapping(path = ["/{id}"])
    fun getMovieByID(@ApiParam("MovieID")
                     @PathVariable("id")
                     pathID: String?): ResponseEntity<MovieDTO> {
        val id: Long?
        try {
            id = pathID!!.toLong()
        } catch (exception: Exception) {
            return ResponseEntity.status(404).build()
        }
        val DTO = crud.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()
        return ResponseEntity.ok(MovieConverter.transform(DTO))
    }

    @ApiOperation("Patch a movie")
    @GetMapping(path = ["/{id}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun updateMovie(@ApiParam("ID of movie being patched")
                    @PathVariable("id")
                    pathID: String?,
                    @ApiParam("Patched movie")
                    @RequestBody
                    DTO: MovieDTO): ResponseEntity<Any> {
        val id: Long
        try {
            id = DTO.id!!.toLong()
        } catch (exception: Exception) {
            return ResponseEntity.status(404).build()
        }
        if (DTO.id != pathID) {
            return ResponseEntity.status(409).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        if (DTO.title == null ||
                DTO.director == null ||
                DTO.info == null ||
                DTO.description == null ||
                DTO.rating == null ||
                DTO.releaseDate == null) {
            return ResponseEntity.status(400).build()
        }
        try {
            crud.patchMovie(
                    id,
                    DTO.title!!,
                    DTO.director!!,
                    DTO.description!!,
                    DTO.info!!,
                    DTO.rating!!,
                    DTO.releaseDate!!
            )
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(404).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Delete movie")
    @DeleteMapping(path = ["/{id}"])
    fun deleteMovie(@ApiParam("Movie ID")
                    @PathVariable("id")
                    pathID: String?): ResponseEntity<Any> {
        val id: Long
        try {
            id = pathID!!.toLong()
        } catch (exception: Exception) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        crud.deleteById(id)
        return ResponseEntity.status(204).build()
    }
}