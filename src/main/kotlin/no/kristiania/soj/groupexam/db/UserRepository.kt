package no.kristiania.soj.groupexam.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.EntityManager

@Repository
interface UserRepository : CrudRepository<UserEntity, String>, UserRepositoryCustom {
}

@Transactional
interface UserRepositoryCustom {

    fun createUser(username: String, password: String): Boolean
}

@Repository
@Transactional
class UserRepositoryImpl : UserRepositoryCustom {

    @Autowired
    private lateinit var em: EntityManager

    override fun createUser(username: String, password: String)
            : Boolean {

        val user = UserEntity(username, password, Collections.singleton("USER"), true)
        em.persist(user)
        return true
    }
}