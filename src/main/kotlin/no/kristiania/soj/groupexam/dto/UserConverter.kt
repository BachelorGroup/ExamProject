package no.kristiania.soj.groupexam.dto

import no.kristiania.soj.groupexam.db.UserEntity

class UserConverter{

    companion object {

        fun transform(userEntity: UserEntity): UserDTO {
            return UserDTO(
                    username = userEntity.username,
                    password = userEntity.password,
                    roles = userEntity.roles,
                    enabled = userEntity.enabled
                    //details = userEntity.details
            )
        }
        fun transform(users: Iterable<UserEntity>): List<UserDTO> {
            return users.map { transform(it) }
        }
    }
}
