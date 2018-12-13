package no.kristiania.soj.groupexam.userservice.dto

import no.kristiania.soj.groupexam.userservice.db.UserDetailsEntity

class UserDetailsConverter{

    companion object {

        fun transform(userDetailsEntity: UserDetailsEntity): UserDetailsDTO {
            return UserDetailsDTO(

                    username = userDetailsEntity.username,
                    name = userDetailsEntity.name,
                    surname = userDetailsEntity.surname,
                    email = userDetailsEntity.email,
                    age = userDetailsEntity.age,
                    purchasedTickets = userDetailsEntity.purchasedTickets

            )
        }
        fun transform(users: Iterable<UserDetailsEntity>): List<UserDetailsDTO> {
            return users.map { transform(it) }
        }
    }
}