package com.example.fundallassessment.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundallassessment.data.local.sharedPrefs.SharedPrefsManager
import com.example.fundallassessment.data.remote.dto.LoginRequestDTO
import com.example.fundallassessment.data.remote.dto.RegisterUserDTO
import com.example.fundallassessment.domain.interactors.SessionManager
import com.example.fundallassessment.domain.models.User
import com.example.fundallassessment.domain.models.UserProfile
import com.example.fundallassessment.domain.repository.Repository
import com.example.fundallassessment.presentation.viewState.Resource
import com.example.fundallassessment.utils.AppConstants.TEMP_USER_DETAILS
import com.example.fundallassessment.utils.EntityMapper.toUser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class FundAllExpensesViewModel @Inject constructor(
    private val repository: Repository,
    private val sessionManager: SessionManager,
    private val sharedPrefsManager: SharedPrefsManager,
    private val gson: Gson,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<User?>> = MutableLiveData()
    val loginResponse: LiveData<Resource<User?>> get() = _loginResponse

    private val _registerResponse: MutableLiveData<Resource<String?>> = MutableLiveData()
    val registerResponse: LiveData<Resource<String?>> get() = _registerResponse

    private val _userDetailsResponse: MutableLiveData<Resource<UserProfile>> = MutableLiveData()
    val userDetailsResponse: LiveData<Resource<UserProfile>> get() = _userDetailsResponse


    fun login(password: String) {
        _loginResponse.value = Resource.Loading()
        viewModelScope.launch(ioDispatcher) {
            val loginRequestDTO = LoginRequestDTO(
                email = fetchUserDetailsFromSharedPref()!!.email,
                password = password
            )
            _loginResponse.postValue(repository.login(loginRequestDTO))
        }
    }

    fun fetchUserDetailsFromSharedPref() : User? {
        return sessionManager.getLoggedInUser() ?: run {
            sharedPrefsManager.retrieveStringFromSharedPrefs(TEMP_USER_DETAILS, "").let {
                if (it.isNotEmpty()){
                    gson.fromJson(it, RegisterUserDTO::class.java).toUser()
                } else {
                    null
                }
            }
        }
    }


    fun register(registerUserDTO: RegisterUserDTO) {
        _registerResponse.value = Resource.Loading()
        viewModelScope.launch(ioDispatcher) {
            sharedPrefsManager.saveStringToSharedPrefs(
                TEMP_USER_DETAILS,
                gson.toJson(registerUserDTO.copy(
                    password = ""
                ))
            )
            _registerResponse.postValue(repository.registerUser(registerUserDTO))
        }
    }

    fun fetchUserDetailsFromBackend(){
        _userDetailsResponse.value = Resource.Loading()
        viewModelScope.launch(ioDispatcher) {
            repository.getUserProfile().collect{
                it.data?.let { it1 -> sessionManager.savedLoggedInUserProfileDetails(it1) }
                _userDetailsResponse.postValue(it)
            }
        }
    }

    fun getUserDetailsFromSharedPref(){
        sessionManager.fetchLoggedInUserProfileDetails()?.let {
            _userDetailsResponse.value = Resource.Success(it)
        } ?: run {
            _userDetailsResponse.value = Resource.Error("NOT FOUND")
        }
    }
}