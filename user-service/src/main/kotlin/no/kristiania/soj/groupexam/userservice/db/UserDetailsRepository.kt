package no.kristiania.soj.groupexam.userservice.db


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Repository
interface UserDetailsRepository : CrudRepository<UserDetailsEntity, String> {
}

@Transactional
interface UserDetailsRepositoryCustom {

    fun createUserDetails(
            username: String,
            name: String,
            surname: String,
            email: String,
            age: Int,
            purchasedTickets: List<Long>): Boolean
}

@Repository
@Transactional
class UserDetailsRepositoryImpl : UserDetailsRepositoryCustom {

    @Autowired
    private lateinit var em: EntityManager

    override fun createUserDetails(
            username: String,
            name: String,
            surname: String,
            email: String,
            age: Int,
            purchasedTickets: List<Long>): Boolean {

        val userDetails = UserDetailsEntity(username, name, surname, email, age, purchasedTickets)
        em.persist(userDetails)
        return true
    }
}