package com.example.fundallassessment.domain.interactors

import com.example.fundallassessment.domain.models.User
import com.example.fundallassessment.domain.models.UserProfile

interface SessionManager {
    fun saveToken(token: String)
    fun fetchToken(): String?
    fun deleteToken()
    fun saveUser(loggedInUser: User)
    fun getLoggedInUser(): User?
    fun removeLoggedInUser()
    fun savedLoggedInUserProfileDetails(userProfile: UserProfile)
    fun fetchLoggedInUserProfileDetails() : UserProfile?
    fun removeLoggedInUserProfileDetails()
}