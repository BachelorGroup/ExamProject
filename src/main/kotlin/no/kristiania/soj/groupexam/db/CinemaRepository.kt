package no.kristiania.soj.groupexam.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import sun.security.krb5.internal.Ticket
import javax.transaction.Transactional

@Repository
interface CinemaRepository : CrudRepository<Ticket, Long>, CinemaRepositoryCustom {
    fun findAllByName(name: String): Iterable<Cinema>

    fun findAllByMovies(movieList: List<Movie>): Iterable<Cinema>

    fun findAllById(id: Long): Iterable<Cinema>
}

@Transactional
interface CinemaRepositoryCustom {

}