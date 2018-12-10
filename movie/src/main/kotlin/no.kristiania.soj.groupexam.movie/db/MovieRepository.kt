package no.kristiania.soj.groupexam.movie.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface MovieRepository : CrudRepository<Movie, Long>, MovieRepositoryCustom {
    fun findAllByMovie(movie: String): Iterable<Movie>

    fun findAllByDirector(director: String): Iterable<Movie>

    fun findAllByMovieAndDirector(movie: String, director: String): Iterable<Movie>

    fun findAllByRating(rating: Int): Iterable<Movie>

    fun findAllByDate(date: String): Iterable<Movie>

    fun findAllById(id: Long): Iterable<Movie>
}

@Transactional
interface MovieRepositoryCustom {
    fun addMovie(title: String,
                 director: String,
                 rating: Int,
                 description: String,
                 info: String,
                 releaseDate: String): Long

    fun updateInfo(id: Long, info: String): Boolean

    fun update(id: Long,
               title: String,
               director: String,
               rating: Int,
               description: String,
               info: String,
               releaseDate: String): Boolean
}