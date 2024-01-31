package com.example.fundallassessment.presentation.viewState

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)
}

fun <T> Fragment.observeLiveData(
    dialog: AlertDialog,
    serverResponse: LiveData<Resource<T>>,
    successAction: (data: T) -> Unit,
){
    serverResponse.observe(this.viewLifecycleOwner) {
        when (it) {
            is Resource.Success -> {
                dialog.dismiss()
                it.data?.let { it1 -> successAction.invoke(it1) }
            }
            is Resource.Loading -> {
                if (!dialog.isShowing){
                    dialog.show()
                }
            }
            is Resource.Error -> {
                dialog.cancel()
                dialog.dismiss()
            }
            else -> {}
        }
    }
}