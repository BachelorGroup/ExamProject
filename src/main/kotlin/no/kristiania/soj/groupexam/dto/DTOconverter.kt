package no.kristiania.soj.groupexam.dto

import no.kristiania.soj.groupexam.db.User

object DTOconverter {


    fun transform(user: User) : UserDTO{

        return UserDTO(
                username = user.username,
                password = user.password,
                email = user.email,
                id = user.id.toString()
        )
    }


    fun transform(users: Iterable<User>) : List<UserDTO>{
        return users.map { transform(it) }
    }
}