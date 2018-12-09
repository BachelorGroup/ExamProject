package no.kristiania.soj.groupexam.service

import no.kristiania.soj.groupexam.db.User
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Service
@Transactional
class UserService(val entityManager: EntityManager) {
    fun getUser(id: Long): User? {
        val user = entityManager.find(User::class.java, id)

        if (user != null) {
            return user
        }
        return null
    }

    fun createUser(username: String, password: String, email: String): Long? {
        val user = User(username = username, password = password, email = email)

        entityManager.persist(user)

        return user.id
    }

    fun deleteUser(userID: Long) {
        val user = entityManager.find(User::class.java, userID)

        if (user != null) {
            entityManager.remove(user)
        }
    }
    fun getUserByUsername(username: String?): User {
        val query: TypedQuery<User> = entityManager.createQuery("select u from User u where u.username=?1", User::class.java)
        query.setParameter(1, username)

        return query.singleResult
    }
    fun getUserByEmail(email: String?): User {
        val query: TypedQuery<User> = entityManager.createQuery("select u from User u where u.email=?1", User::class.java)
        query.setParameter(1, email)
        return query.singleResult
    }
}