package no.kristiania.soj.groupexam.movie.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
interface MovieRepository : CrudRepository<Movie, Long>, MovieRepositoryCustom

@Transactional
interface MovieRepositoryCustom {
    fun addMovie(
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: String
    ) : Long

    fun patchMovie(
            id: Long,
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: String
    ) : Boolean
}

@Repository
@Transactional
class MovieRepositoryImplementation : MovieRepositoryCustom {
    @Autowired
    private lateinit var entityManager: EntityManager

    override fun addMovie(
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: String
    ): Long {
        val movie = Movie(title, director, description, info, rating, releaseDate)
        entityManager.persist(movie)
        return movie.id!!
    }

    override fun patchMovie(
            id: Long,
            title: String,
            director: String,
            description: String,
            info: String,
            rating: Int,
            releaseDate: String
    ) : Boolean {
        val movie = entityManager.find(Movie::class.java, id) ?: return false

        movie.title = title
        movie.director = director
        movie.description = description
        movie.info = info
        movie.rating = rating
        movie.releaseDate = releaseDate

        return true
    }
}