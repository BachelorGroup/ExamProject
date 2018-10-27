/*package no.kristiania.soj.groupexam.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@Repository
interface UserDetailsRepository : CrudRepository<UserDetailsEntity, String> {
}
/*
@Transactional
interface UserDetailsRepositoryCustom {

    fun createUserDetails(
            name: String,
            lastName: String,
            email: String,
            age: Int,
            purchasedTickets: List<TicketEntity>,
            username: String): Boolean
}
*//*
@Repository
@Transactional
class UserDetailsRepositoryImpl : UserDetailsRepositoryCustom {

    @Autowired
    private lateinit var em: EntityManager*/
    /*
        @Autowired
        private lateinit var passwordEncoder: PasswordEncoder
    *//*
    override fun createUserDetails(
            name: String,
            lastName: String,
            email: String,
            age: Int,
            purchasedTickets: List<TicketEntity>,
            username: String): Boolean {

        val userDetails = UserDetailsEntity(name, lastName, email, age, purchasedTickets, username)
        em.persist(userDetails)
        return true
    }
}*/