package no.kristiania.soj.groupexam.movie.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
interface MovieRepository : CrudRepository<Movie, Long>, MovieRepositoryCustom {
}

@Transactional
interface MovieRepositoryCustom {
    fun addMovie(title: String,
                 director: String,
                 rating: Int,
                 description: String,
                 info: String,
                 releaseDate: String): Long

    fun update(id: Long,
               title: String,
               director: String,
               rating: Int,
               description: String,
               info: String,
               releaseDate: String): Boolean
}
@Repository
@Transactional
class MovieRepositoryImplementation : MovieRepositoryCustom {
    @Autowired
    private lateinit var entityManager: EntityManager

    override fun addMovie(title: String, director: String, rating: Int, description: String, info: String, releaseDate: String): Long {
       val movie = Movie(title, director, description, info, rating, releaseDate)
        entityManager.persist(movie)

        return movie.id!!
    }

    override fun update(id: Long, title: String, director: String, rating: Int, description: String, info: String, releaseDate: String): Boolean {
        val movie = entityManager.find(Movie::class.java, id) ?: return false

        movie.title = title
        movie.director = director
        movie.rating = rating
        movie.description = description
        movie.releaseDate = releaseDate

        return true
    }
}