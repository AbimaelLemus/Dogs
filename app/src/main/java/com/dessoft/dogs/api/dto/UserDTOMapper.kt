package com.dessoft.dogs.api.dto

import com.dessoft.dogs.model.User

class UserDTOMapper {

    fun fromUserDTOToUserDomain(userDTO: UserDTO): User {
        return User(userDTO.id, userDTO.email, userDTO.authenticationToken)
    }

}