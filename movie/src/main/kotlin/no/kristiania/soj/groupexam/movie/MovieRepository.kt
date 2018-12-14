package no.kristiania.soj.groupexam.movie

import no.kristiania.soj.groupexam.movie.db.MovieEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Repository
interface MovieRepository : CrudRepository<MovieEntity, Long>, MovieRepositoryCustom {

}

@Transactional
interface MovieRepositoryCustom {
    fun createMovie(
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: LocalDateTime
    ): Long

    fun update(id: Long,
               title: String,
               director: String,
               description: String,
               info: String,
               rating: Int,
               releaseDate: LocalDateTime): Boolean

    fun updateRating(id: Long, rating: Int): Boolean

    fun updateTitle(id: Long, title: String): Boolean

    fun updateDirector(id: Long, director: String): Boolean

    fun updateDescription(id: Long, description: String): Boolean

    fun updateInfo(id: Long, info: String): Boolean

//    fun updateReleaseDate(id: Long, releaseDate: LocalDateTime): Boolean
}

@Repository
@Transactional
class MovieRepositoryImpl : MovieRepositoryCustom {
    @Autowired
    private lateinit var entityManager: EntityManager

    override fun createMovie(
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: LocalDateTime
    ): Long {
        val movie = MovieEntity(title, director, description, info, rating, releaseDate)
        entityManager.persist(movie)
        return movie.id!!
    }

    override fun update(id: Long, title: String, director: String, description: String, info: String, rating: Int, releaseDate: LocalDateTime): Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
        movie.title = title
        movie.director = director
        movie.description = description
        movie.rating = rating
        movie.releaseDate = releaseDate
        return true
    }

    override fun updateRating(id: Long, rating: Int): Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
        movie.rating = rating
        return true
    }

    override fun updateTitle(id: Long, title: String): Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
        movie.title = title
        return true
    }

    override fun updateDirector(id: Long, director: String): Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
        movie.director = director
        return true
    }

    override fun updateDescription(id: Long, description: String): Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
        movie.description = description
        return true
    }

    override fun updateInfo(id: Long, info: String): Boolean {
        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
        movie.info = info
        return true
    }

//    override fun updateReleaseDate(id: Long, releaseDate: LocalDateTime): Boolean {
//        val movie = entityManager.find(MovieEntity::class.java, id) ?: return false
//        movie.releaseDate = releaseDate
//        return true
//    }
}