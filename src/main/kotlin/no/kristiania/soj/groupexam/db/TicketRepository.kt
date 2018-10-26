package no.kristiania.soj.groupexam.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Repository
interface TicketRepository : CrudRepository<TicketEntity, Long>, TicketRepositoryCustom {
}

@Transactional
interface TicketRepositoryCustom {

    fun createTicket(cinema: String, hall: Int, seatRow: Int, seatColumn: Int, movieTitle: String, movieDateTime: LocalDateTime): Long

    fun update(ticketId: Long, cinema: String, hall: Int, seatRow: Int, seatColumn: Int, movieTitle: String, movieDateTime: LocalDateTime): Boolean

    fun updateSeat(ticketId: Long, seatRow: Int, seatColumn: Int) : Boolean
}

@Repository
@Transactional
class TicketRepositoryImpl : TicketRepositoryCustom {

    @Autowired
    private lateinit var em: EntityManager

    override fun createTicket(cinema: String, hall: Int, seatRow: Int, seatColumn: Int, movieTitle: String, movieDateTime: LocalDateTime)
            : Long {

        val entity = TicketEntity(cinema, hall, seatRow, seatColumn, movieTitle, movieDateTime, LocalDateTime.now())
        em.persist(entity)
        return entity.id!!
    }

    override fun update(ticketId: Long, cinema: String, hall: Int, seatRow: Int, seatColumn: Int, movieTitle: String, movieDateTime: LocalDateTime)
            : Boolean {

        val ticket = em.find(TicketEntity::class.java, ticketId) ?: return false

        ticket.cinema = cinema
        ticket.hall = hall
        ticket.seatRow = seatRow
        ticket.seatColumn = seatColumn
        ticket.movieTitle = movieTitle
        ticket.movieDateTime = movieDateTime

        return true
    }

    override fun updateSeat(ticketId: Long, seatRow: Int, seatColumn: Int)
            : Boolean {

        val ticket = em.find(TicketEntity::class.java, ticketId) ?: return false

        ticket.seatRow = seatRow
        ticket.seatColumn = seatColumn

        return true
    }
}