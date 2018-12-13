package no.kristiania.soj.groupexam.movie.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import javax.persistence.EntityManager

@Repository
interface MovieRepository : CrudRepository<MovieEntity, Long>, MovieRepositoryCustom

@Transactional
interface MovieRepositoryCustom {
    fun createMovie(
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: ZonedDateTime
    ) : Long

    fun update(
            id: Long,
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: ZonedDateTime
    ) : Boolean
}

@Repository
@Transactional
class MovieRepositoryImplementation : MovieRepositoryCustom {
    @Autowired
    private lateinit var entityManager: EntityManager

    override fun createMovie(
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: ZonedDateTime
    ): Long {
        val movie = MovieEntity(title, director, description, info, rating, releaseDate)
        entityManager.persist(movie)
        return movie.id!!
    }

    override fun update(
            id: Long,
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: ZonedDateTime
    ) : Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false

        movie.title = title
        movie.director = director
        movie.description = description
        movie.info = info
        movie.rating = rating
        movie.releaseDate = releaseDate

        return true
    }
}