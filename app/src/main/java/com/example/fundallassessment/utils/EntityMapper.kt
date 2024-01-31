package com.example.fundallassessment.utils

import com.example.fundallassessment.data.remote.apiResponses.UserDTO
import com.example.fundallassessment.data.remote.apiResponses.UserProfileDTO
import com.example.fundallassessment.data.remote.dto.RegisterUserDTO
import com.example.fundallassessment.domain.models.User
import com.example.fundallassessment.domain.models.UserProfile

object EntityMapper {

    fun UserProfileDTO.toUserProfile(): UserProfile =
        UserProfile(avatar, email, firstname, id.toString(), lastname, total_balance, spent, income)

    fun UserDTO.toUser(): User = User(
        avatar,
        email,
        firstname,
        id.toString(),
        lastname,
        monthly_target.toString()
    )

    fun RegisterUserDTO.toUser(): User = User(
        "",
        email,
        firstname,
        "",
        lastname,
        ""
    )
}