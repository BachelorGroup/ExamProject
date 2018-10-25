package no.kristiania.soj.groupexam.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import sun.security.krb5.internal.Ticket

@Repository
interface CinemaRepository : CrudRepository<Ticket, Long>