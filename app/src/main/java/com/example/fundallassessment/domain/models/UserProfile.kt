package com.example.fundallassessment.domain.models

data class UserProfile(
    val profileImage: String,
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val totalBalance: String,
    val spent: String,
    val income: String,
)