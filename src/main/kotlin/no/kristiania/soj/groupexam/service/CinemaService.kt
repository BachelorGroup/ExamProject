package no.kristiania.soj.groupexam.service

import no.kristiania.soj.groupexam.db.Cinema
import no.kristiania.soj.groupexam.db.Movie
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Service
@Transactional
class CinemaService(val entityManager: EntityManager) {
    fun getCinema(id: Long): Cinema? {
        val cinema = entityManager.find(Cinema::class.java, id)
        if (cinema != null) {
            return cinema
        }
        return null
    }

    fun createCinema(name: String, movies: List<Movie>, halls: String): Long? {
        val cinema = Cinema(name = name, movies = movies, halls = halls)

        entityManager.persist(cinema)

        return cinema.id
    }

    fun deleteCinema(cinemaID: Long) {
        val cinema = entityManager.find(Cinema::class.java, cinemaID)
        if (cinema != null) {
            entityManager.remove(cinema)
        }
    }

    fun getCinemaByName(name: String?): List<Cinema> {
        val query: TypedQuery<Cinema>
        if (name == null) {
            query = entityManager.createQuery("select c from Cinema c", Cinema::class.java)
        } else {
            query = entityManager.createQuery("select c from Cinema c where c.name=?1", Cinema::class.java)
            query.setParameter(1, name)
        }
        val result = query.resultList
        return result
    }
}