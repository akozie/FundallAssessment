package com.example.fundallassessment.domain.usecases

import com.example.fundallassessment.data.local.sharedPrefs.SharedPrefsManager
import com.example.fundallassessment.domain.interactors.SessionManager
import com.example.fundallassessment.domain.models.User
import com.example.fundallassessment.domain.models.UserProfile
import com.example.fundallassessment.utils.AppConstants.STRING_LOGGED_IN_USER_PROFILE_SHARED_PREFS_KEY
import com.example.fundallassessment.utils.AppConstants.STRING_LOGGED_IN_USER_SHARED_PREFS_KEY
import com.example.fundallassessment.utils.AppConstants.STRING_SESSION_TOKEN_SHARED_PREFS_KEY
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor(
    private val appSharedPref: SharedPrefsManager,
    private val gson: Gson
) : SessionManager {
    override fun saveToken(token: String) {
        appSharedPref.saveStringToSharedPrefs(STRING_SESSION_TOKEN_SHARED_PREFS_KEY, token)
    }

    override fun fetchToken(): String? =
        appSharedPref.retrieveStringFromSharedPrefs(STRING_SESSION_TOKEN_SHARED_PREFS_KEY, "").let {
            if (it.isNotEmpty()) it else null
        }

    override fun deleteToken() {
        appSharedPref.removePref(STRING_SESSION_TOKEN_SHARED_PREFS_KEY)
    }

    override fun saveUser(loggedInUser: User) {
        appSharedPref.saveStringToSharedPrefs(
            STRING_LOGGED_IN_USER_SHARED_PREFS_KEY,
            gson.toJson(loggedInUser)
        )
    }

    override fun getLoggedInUser(): User? =
        appSharedPref.retrieveStringFromSharedPrefs(STRING_LOGGED_IN_USER_SHARED_PREFS_KEY, "")
            .let {
                if (it.isNotEmpty()) gson.fromJson(it, User::class.java) else null
            }

    override fun removeLoggedInUser() {
        appSharedPref.removePref(STRING_LOGGED_IN_USER_SHARED_PREFS_KEY)
        removeLoggedInUserProfileDetails()
    }

    override fun savedLoggedInUserProfileDetails(userProfile: UserProfile) {
        appSharedPref.saveStringToSharedPrefs(STRING_LOGGED_IN_USER_PROFILE_SHARED_PREFS_KEY, gson.toJson(userProfile))
    }

    override fun fetchLoggedInUserProfileDetails(): UserProfile? {
        return appSharedPref.retrieveStringFromSharedPrefs(STRING_LOGGED_IN_USER_PROFILE_SHARED_PREFS_KEY, "").let {
            if (it.isNotEmpty()) gson.fromJson(it, UserProfile::class.java) else null
        }

    }

    override fun removeLoggedInUserProfileDetails() {
        appSharedPref.removePref(STRING_LOGGED_IN_USER_PROFILE_SHARED_PREFS_KEY)
    }

}