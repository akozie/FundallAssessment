package com.example.fundallassessment.data.remote.dto

data class RegisterUserDTO(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String,
    val password_confirmation: String
)