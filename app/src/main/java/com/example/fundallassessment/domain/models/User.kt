package com.example.fundallassessment.domain.models

data class User(
    val userProfileImage: String,
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val monthlyTarget: String,
)