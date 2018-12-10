package no.kristiania.soj.groupexam.movie.service

import no.kristiania.soj.groupexam.movie.db.Movie
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Service
@Transactional
class MovieService(val entityManager: EntityManager) {

    fun getMovie(id: Long): Movie? {
        val movie = entityManager.find(Movie::class.java, id)
        if (movie != null) {
            return movie
        }
        return null
    }

    fun createMovie(title: String,
                    director: String,
                    description: String,
                    info: String,
                    rating: Int,
                    releaseDate: String): Long? {
        val movie = Movie(title = title,
                director = director,
                description = description,
                info = info,
                rating = rating,
                releaseDate = releaseDate)
        entityManager.persist(movie)

        return movie.id
    }

    fun deleteMovie(movieID: Long) {
        val movie = entityManager.find(Movie::class.java, movieID)
        if (movie != null) {
            entityManager.remove(movie)
        }
    }

    fun getMoviesByDirector(director: String?): List<Movie> {
        val query: TypedQuery<Movie>
        if (director == null) {
            query = entityManager.createQuery("select m from Movie m", Movie::class.java)
        } else {
            query = entityManager.createQuery("select m from Movie m where m.director=?1", Movie::class.java)
            query.setParameter(1, director)
        }
        return query.resultList
    }

    fun getMoviesByRating(rating: Int): List<Movie> {
        val query: TypedQuery<Movie>
        if (rating == null) {
            query = entityManager.createQuery("select m from Movie m", Movie::class.java)
        } else {
            query = entityManager.createQuery("select m from Movie m where m.rating=?1", Movie::class.java)
            query.setParameter(1, rating)
        }
        return query.resultList
    }

    fun getMoviesByDate(releaseDate: String): List<Movie> {
        val query: TypedQuery<Movie>
        if (releaseDate == null) {
            query = entityManager.createQuery("select m from Movie m", Movie::class.java)
        } else {
            query = entityManager.createQuery("select m from Movie m where m.releaseDate=?1", Movie::class.java)
            query.setParameter(1, releaseDate)
        }
        return query.resultList
    }
}