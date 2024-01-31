package com.example.fundallassessment.domain.repository

import com.example.fundallassessment.data.remote.apiServices.FundallApiService
import com.example.fundallassessment.data.remote.dto.LoginRequestDTO
import com.example.fundallassessment.data.remote.dto.RegisterUserDTO
import com.example.fundallassessment.domain.interactors.SessionManager
import com.example.fundallassessment.domain.models.User
import com.example.fundallassessment.domain.models.UserProfile
import com.example.fundallassessment.presentation.viewState.Resource
import com.example.fundallassessment.utils.EntityMapper.toUser
import com.example.fundallassessment.utils.EntityMapper.toUserProfile
import com.example.fundallassessment.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val fundAllApiService: FundallApiService,
    private val networkUtils: NetworkUtils,
    private val sessionManager: SessionManager
) : Repository {
    override suspend fun registerUser(registerUserRequest: RegisterUserDTO): Resource<String?> =
        fundAllApiService.registerUser(registerUserRequest).let {
            networkUtils.getServerResponse(it).let { resource ->
                resource.let { res ->
                    res.data?.let { resp ->
                        Resource.Success(resp.success.message)
                    } ?: run { Resource.Error("Failed") }
                }
            }
        }

    override suspend fun login(loginRequestDTO: LoginRequestDTO): Resource<User?> =
        fundAllApiService.login(loginRequestDTO).let {
            networkUtils.getServerResponse(it).let { response ->
                if (response is Resource.Success) {
                    response.data?.let { loginResponse ->
                        sessionManager.saveUser(loginResponse.success.user.toUser())
                        sessionManager.saveToken(loginResponse.success.user.access_token)
                        Resource.Success(loginResponse.success.user.toUser())
                    } ?: run { Resource.Error("Failed") }
                } else {
                    Resource.Error("Failed")
                }
            }
        }

    override fun getUserProfile(): Flow<Resource<UserProfile>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = networkUtils.getServerResponse(fundAllApiService.getUserProfile())
               val result = if (response is Resource.Success) {
                    response.data?.let {
                        Resource.Success(it.success.data.toUserProfile())
                    } ?: run { Resource.Error("Failed") }
                } else {
                    Resource.Error("Failed")
                }
                emit(result)
            } catch (e: Exception) {
                emit(networkUtils.handleError(e))
            }
        }
}