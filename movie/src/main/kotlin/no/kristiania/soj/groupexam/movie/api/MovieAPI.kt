package no.kristiania.soj.groupexam.movie.api

import com.google.common.base.Throwables
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import no.kristiania.soj.groupexam.movie.dto.MovieConverter
import no.kristiania.soj.groupexam.movie.dto.MovieDTO
import no.kristiania.soj.groupexam.movie.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.ConstraintViolationException

@RestController
@RequestMapping(path = ["/api/movie"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@Validated
class MovieAPI {

    @Autowired
    private lateinit var crud: MovieRepository

    @ApiOperation("Create movie")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ApiResponse(code = 201, message = "MovieEntity is persisted")
    fun createMovie(@ApiParam("All info about movies that are created")
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
            id = crud.createMovie(
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
    fun getAll(): ResponseEntity<List<MovieDTO>> {
        val list = crud.findAll()
        return ResponseEntity.ok(MovieConverter.transform(list))
    }

    @ApiOperation("Get movie by ID")
    @GetMapping(path = ["/{id}"])
    fun getMovieByID(@ApiParam("MovieID")
                     @PathVariable("id")
                     pathId: String?): ResponseEntity<MovieDTO> {
        val id: Long?
        try {
            id = pathId!!.toLong()
        } catch (exception: Exception) {
            return ResponseEntity.status(404).build()
        }
        val dto = crud.findById(id).orElse(null) ?: return ResponseEntity.status(404).build()
        return ResponseEntity.ok(MovieConverter.transform(dto))
    }

    @ApiOperation("Update movie")
    @PutMapping(path = ["/{id}"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun updateMovie(
            @ApiParam("The ID of the movie")
            @PathVariable("id")
            pathId: String?,
            @ApiParam("Updated Movie ID")
            @RequestBody
            dto: MovieDTO

    ): ResponseEntity<Any> {

        val dtoId: Long
        try {
            dtoId = dto.id!!.toLong()
        } catch (exception: Exception) {

            return ResponseEntity.status(404).build()
        }

        if (dto.id != pathId) {

            return ResponseEntity.status(409).build()
        }

        if (!crud.existsById(dtoId)) {

            return ResponseEntity.status(404).build()
        }

        if (dto.title == null || dto.director == null || dto.description == null || dto.info == null || dto.rating == null || dto.releaseDate == null) {
            return ResponseEntity.status(400).build()
        }

        try {
            crud.update(dtoId, dto.title!!, dto.director!!, dto.description!!, dto.info!!, dto.rating!!, dto.releaseDate!!)
        } catch (e: Exception) {
            if (Throwables.getRootCause(e) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw e
        }

        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the title")
    @PatchMapping(path = ["/{id}/title"], consumes = [(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)])
    fun updateTitle(@ApiParam("the title of the movie")
                    @PathVariable("id")
                    id: Long?, @ApiParam("the new title") title: String): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        try {
            crud.updateTitle(id, title)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the director")
    @PatchMapping(path = ["/{id}/director"], consumes = [(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)])
    fun updateDirector(@ApiParam("the director of the movie")
                       @PathVariable("id")
                       id: Long?, @ApiParam("the new director") director: String): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        try {
            crud.updateDirector(id, director)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the description")
    @PatchMapping(path = ["/{id}/description"], consumes = [(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)])
    fun updateDescription(@ApiParam("the description of the movie")
                          @PathVariable("id")
                          id: Long?, @ApiParam("the new description") description: String): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        try {
            crud.updateDescription(id, description)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the info")
    @PatchMapping(path = ["/{id}/info"], consumes = [(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)])
    fun updateInfo(@ApiParam("the info of the movie")
                   @PathVariable("id")
                   id: Long?, @ApiParam("the new info") info: String): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        try {
            crud.updateInfo(id, info)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the rating")
    @PatchMapping(path = ["/{id}/rating"], consumes = [(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)])
    fun updateRating(@ApiParam("the id of the movie")
                     @PathVariable("id")
                     id: Long?, @ApiParam("the new rating") rating: Int): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        try {
            crud.updateRating(id, rating)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Update the release date")
    @PatchMapping(path = ["/{id}/releaseDate"], consumes = [(org.springframework.http.MediaType.APPLICATION_JSON_VALUE)])
    fun updateReleaseDate(@ApiParam("the release date of the movie")
                          @PathVariable("id")
                          id: Long?, @ApiParam("the new date") releaseDate: LocalDateTime): ResponseEntity<Any> {
        if (id == null) {
            return ResponseEntity.status(400).build()
        }
        if (!crud.existsById(id)) {
            return ResponseEntity.status(404).build()
        }
        try {
            crud.updateReleaseDate(id, releaseDate)
        } catch (exception: Exception) {
            if (Throwables.getRootCause(exception) is ConstraintViolationException) {
                return ResponseEntity.status(400).build()
            }
            throw exception
        }
        return ResponseEntity.status(204).build()
    }

    @ApiOperation("Delete movie")
    @DeleteMapping(path = ["/{id}"])
    fun deleteMovie(@ApiParam("MovieEntity ID")
                    @PathVariable("id")
                    pathId: String?): ResponseEntity<Any> {
        val id: Long
        try {
            id = pathId!!.toLong()
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