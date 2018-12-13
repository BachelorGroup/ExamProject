package no.kristiania.soj.groupexam.auth.dto

import no.kristiania.soj.groupexam.auth.db.UserEntity

class UserConverter{

    companion object {

        fun transform(userEntity: UserEntity): UserDTO {
            return no.kristiania.soj.groupexam.auth.dto.UserDTO(
                    username = userEntity.username,
                    password = userEntity.password,
                    roles = userEntity.roles
            )
        }
        fun transform(users: Iterable<UserEntity>): List<UserDTO> {
            return users.map { transform(it) }
        }
    }
}