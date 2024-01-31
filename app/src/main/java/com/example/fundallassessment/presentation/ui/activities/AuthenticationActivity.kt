package com.example.fundallassessment.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fundallassessment.R
import com.example.fundallassessment.presentation.viewModel.FundAllExpensesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private val viewModel by viewModels<FundAllExpensesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_FundallAssessment)
        super.onCreate(savedInstanceState)

        if (viewModel.fetchUserDetailsFromSharedPref()?.email == null){
            setContentView(R.layout.activity_authentication)
        }else{
            setContentView(R.layout.activity_main)
        }
    }
}