package no.kristiania.soj.groupexam.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : CrudRepository<Movie, Long> {
    fun findAllByMovie(movie: String): Iterable<Movie>

    fun findAllByDirector(director: String): Iterable<Movie>

    fun findAllByMovieAndDirector(movie: String, director: String): Iterable<Movie>
}