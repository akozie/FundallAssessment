package com.example.fundallassessment.data.remote.apiServices

import com.example.fundallassessment.data.remote.annotations.AuthenticatedRequest
import com.example.fundallassessment.data.remote.apiResponses.GeneralSuccessResponse
import com.example.fundallassessment.data.remote.apiResponses.GetUserProfileResponse
import com.example.fundallassessment.data.remote.apiResponses.LoginResponse
import com.example.fundallassessment.data.remote.dto.LoginRequestDTO
import com.example.fundallassessment.data.remote.dto.RegisterUserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FundallApiService {
    @POST("api/v1/register")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserDTO): Response<GeneralSuccessResponse>

    @POST("api/v1/login")
    suspend fun login(@Body loginRequestDTO: LoginRequestDTO): Response<LoginResponse>

    @GET("api/v1/base/profile")
    @AuthenticatedRequest
    suspend fun getUserProfile(): Response<GetUserProfileResponse>
}