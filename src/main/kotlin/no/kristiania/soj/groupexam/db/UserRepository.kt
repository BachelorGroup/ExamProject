package no.kristiania.soj.groupexam.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UserRepository : CrudRepository<User, Long>, UserRepositoryCustom {
    fun findAllByUser(user: String): Iterable<User>

    fun findAllByEmail(email: String): Iterable<User>

    fun findAllById(id: Long) : Iterable<User>
}

@Transactional
interface UserRepositoryCustom {
    fun addUser(username: String,
                password: String,
                email: String): Long

    fun updateUsername(id: Long, username: String): Boolean

    fun updatePassword(id: Long, password: String): Boolean

    fun updateEmail(id: Long, email: String): Boolean

    fun update(id: Long,
               username: String,
               password: String,
               email: String): Boolean
}
