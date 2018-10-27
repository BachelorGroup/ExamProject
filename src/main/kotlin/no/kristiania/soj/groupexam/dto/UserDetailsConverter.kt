/*package no.kristiania.soj.groupexam.dto

import no.kristiania.soj.groupexam.db.UserDetailsEntity

class UserDetailsConverter{

    companion object {

        fun transform(userDetailsEntity: UserDetailsEntity): UserDetailsDTO {
            return UserDetailsDTO(
                    name = userDetailsEntity.name,
                    lastName = userDetailsEntity.lastName,
                    email = userDetailsEntity.email,
                    age = userDetailsEntity.age,
                    purchasedTickets = userDetailsEntity.purchasedTickets,
                    username = userDetailsEntity.username

            )
        }
        fun transform(users: Iterable<UserDetailsEntity>): List<UserDetailsDTO> {
            return users.map { transform(it) }
        }
    }
}
*/