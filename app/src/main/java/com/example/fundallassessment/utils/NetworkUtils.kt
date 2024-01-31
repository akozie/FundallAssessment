package com.example.fundallassessment.utils

import com.example.fundallassessment.presentation.viewState.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkUtils() {
    fun <T> getServerResponse(serverResponse: Response<T>): Resource<T?> {
        return when {
            serverResponse.code() in 200..299 -> {
                Resource.Success(serverResponse.body()!!)
            }

            serverResponse.code() in 400..499 -> {
                Resource.Error("Client Error")
            }

            serverResponse.code() >= 500 -> {
                Resource.Error("Server Error")
            }

            else -> {
                Resource.Error("Error")
            }
        }
    }

    fun <T> handleError(e: Exception): Resource<T> =
        when (e) {
            is SocketTimeoutException -> {
                Resource.Error("Time out")
            }

            is HttpException -> {
                Resource.Error(e.localizedMessage!!)
            }

            is IOException -> {
                Resource.Error("Connection Error")
            }

            else -> {
                Resource.Error(e.localizedMessage!!)
            }
        }
}