package no.kristiania.soj.groupexam.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CinemaRepository : CrudRepository<Ticket, Long>