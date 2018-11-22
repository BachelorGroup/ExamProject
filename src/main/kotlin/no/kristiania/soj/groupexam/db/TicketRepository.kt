package no.kristiania.soj.groupexam.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import javax.persistence.EntityManager

@Repository
interface TicketRepository : CrudRepository<TicketEntity, Long>, TicketRepositoryCustom {
}

@Transactional
interface TicketRepositoryCustom {

    fun createTicket(cinema: String,
                     hall: Int,
                     seatRow: Int,
                     seatColumn: Int,
                     movieTitle: String,
                     movieDateTime: LocalDateTime): Long
}

@Repository
@Transactional
class TicketRepositoryImpl : TicketRepositoryCustom {

    @Autowired
    private lateinit var em: EntityManager

    override fun createTicket(cinema: String,
                              hall: Int,
                              seatRow: Int,
                              seatColumn: Int,
                              movieTitle: String,
                              movieDateTime: LocalDateTime)
            : Long {

        val entity = TicketEntity(cinema,
                hall,
                seatRow,
                seatColumn,
                movieTitle,
                movieDateTime,
                LocalDateTime.now())
        em.persist(entity)
        return entity.id!!
    }
}