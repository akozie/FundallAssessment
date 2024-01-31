package com.example.fundallassessment.data.remote.apiResponses

data class UserProfileDTO(
    val avatar: String,
    val email: String,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val total_balance: String,
    val spent: String,
    val income: String,
)