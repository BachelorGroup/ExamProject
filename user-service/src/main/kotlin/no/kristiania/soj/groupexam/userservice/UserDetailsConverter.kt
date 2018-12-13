package no.kristiania.soj.groupexam.userservice

import no.kristiania.soj.groupexam.userservice.dto.UserDetailsDTO

class UserDetailsConverter{

    companion object {

        fun transform(userDetailsEntity: UserDetailsEntity): UserDetailsDTO {
            return UserDetailsDTO(

                    username = userDetailsEntity.username,
                    name = userDetailsEntity.name,
                    surname = userDetailsEntity.surname,
                    email = userDetailsEntity.email,
                    age = userDetailsEntity.age

            )
        }
        fun transform(users: Iterable<UserDetailsEntity>): List<UserDetailsDTO> {
            return users.map { transform(it) }
        }
    }
}