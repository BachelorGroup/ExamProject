package no.kristiania.soj.groupexam.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
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
    fun addMovie(title: String, director: String, rating: Int, description: String, info: String, releaseDate: String): Long

    fun updateInfo(id: Long, info: String): Boolean

    fun update(id: Long, title: String, director: String, rating: Int, description: String, info: String, releaseDate: String): Boolean
}

//@Repository
//@Transactional
//class MovieRepositoryImpl : MovieRepositoryCustom {
//    @Autowired
//    private lateint var em: EntityManager
//    override fun addMovie(id: Long, title: String, director: String, rating: Int, description: String, info: String, releaseDate: String): Long {
//        val entity = Movie(title, description, info, rating, releaseDate, id)
//        em.persist(entity)
//        return entity.id!!
//    }
//
//    override fun updateInfo(id: Long, info: String): Boolean {
//        val movie = em.find(Movie::class.java, id) ?: return false
//        movie.info = info
//        return true
//    }
//    override fun update(id: Long, title: String, director: String, rating: Int, description: String, info: String, releaseDate: String) : Boolean {
//        val movie = em.find(Movie::class.java, id) ?: return false
//        movie.title = title
//        movie.director = director
//        movie.rating = rating
//        movie.description = description
//        movie.info = info
//        movie.releaseDate = releaseDate
//        return true
//    }
//}