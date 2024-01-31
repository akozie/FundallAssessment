package com.example.fundallassessment.domain.repository

import com.example.fundallassessment.data.remote.apiResponses.GeneralSuccessResponse
import com.example.fundallassessment.data.remote.apiResponses.GetUserProfileResponse
import com.example.fundallassessment.data.remote.apiResponses.LoginResponse
import com.example.fundallassessment.data.remote.dto.LoginRequestDTO
import com.example.fundallassessment.data.remote.dto.RegisterUserDTO
import com.example.fundallassessment.domain.models.User
import com.example.fundallassessment.domain.models.UserProfile
import com.example.fundallassessment.presentation.viewState.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun registerUser(registerUserRequest: RegisterUserDTO): Resource<String?>
    suspend fun login(loginRequestDTO: LoginRequestDTO): Resource<User?>
    fun getUserProfile(): Flow<Resource<UserProfile>>
}