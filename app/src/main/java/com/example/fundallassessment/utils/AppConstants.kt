package com.example.fundallassessment.utils

import android.app.AlertDialog
import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fundallassessment.R

object AppConstants {
    const val BASE_URL = "https://expense-api.fundall.io/"

    /**
     * SHARED PREFERENCES KEYS
     * */
    const val STRING_SESSION_TOKEN_SHARED_PREFS_KEY = "string_session_token_shared_prefs_key"
    const val STRING_LOGGED_IN_USER_SHARED_PREFS_KEY = "string_logged_in_user_shared_prefs_key"
    const val STRING_LOGGED_IN_USER_PROFILE_SHARED_PREFS_KEY =
        "string_logged_in_user_profile_shared_prefs_key"
    const val TEMP_USER_DETAILS = "temp_user_details"

    /**
     * INTERCEPTOR TAGS
     * */
    const val STRING_LOGGING_INTERCEPTOR_TAG = "logging_interceptor"
    const val STRING_AUTH_INTERCEPTOR_TAG = "auth_interceptor"

    /**
     * CALL TIMEOUT
     * */
    const val TIME_OUT_15 = 15L

    /**
     * TOAST
     * */
    fun Fragment.showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * VALIDATE PASSWORD
     * */
    fun validatePasswordMismatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /**
     * ALERT DIALOG
     * */
    fun alertDialog(
        context: Context
    ): AlertDialog {
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.layout_custom_loading_dialog, null)
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(dialogView)
        return dialogBuilder.create()
    }

    /**
     * PASSWORD TOGGLE
     * */
    fun togglePasswordVisibility(editText: EditText, isVisible: Boolean) {
        if (isVisible) {
            // Show password
            editText.transformationMethod = null
        } else {
            // Hide password
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        // Move cursor to the end of the text
        editText.setSelection(editText.text.length)
    }

}