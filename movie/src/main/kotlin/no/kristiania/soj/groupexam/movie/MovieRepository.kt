package no.kristiania.soj.groupexam.movie

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Repository
interface MovieRepository : CrudRepository<MovieEntity, Long>, MovieRepositoryCustom{

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
    ) : Long
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
}