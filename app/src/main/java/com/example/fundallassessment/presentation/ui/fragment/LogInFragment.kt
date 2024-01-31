package com.example.fundallassessment.presentation.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentLogInBinding
import com.example.fundallassessment.presentation.ui.activities.MainActivity
import com.example.fundallassessment.presentation.viewModel.FundAllExpensesViewModel
import com.example.fundallassessment.presentation.viewState.Resource
import com.example.fundallassessment.presentation.viewState.observeLiveData
import com.example.fundallassessment.utils.AppConstants.alertDialog
import com.example.fundallassessment.utils.AppConstants.showToast
import com.example.fundallassessment.utils.AppConstants.togglePasswordVisibility
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel by activityViewModels<FundAllExpensesViewModel>()
    private lateinit var dialog: AlertDialog
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        binding.apply {
            viewModel.fetchUserDetailsFromSharedPref()?.let {
                userDetails = it
            }
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = alertDialog(requireContext())

        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment2)
        }

        binding.loginButton.setOnClickListener {
            if (viewModel.fetchUserDetailsFromSharedPref()?.email == null) {
                showToast(getString(R.string.kindly_sign_up))
                return@setOnClickListener
            }
            loginUser()
        }

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment2)
        }

        binding.passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.password, isPasswordVisible)
        }

    }


    private fun loginUser() {
        viewModel.login(binding.password.text.toString().trim())
        dialog.show()
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    viewModel.fetchUserDetailsFromBackend()
                    observeLiveData(
                        dialog,
                        viewModel.userDetailsResponse
                    ) {
                        val intent = Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                is Resource.Loading -> {
                    dialog.show()
                }
                is Resource.Error -> {
                    dialog.cancel()
                    dialog.dismiss()
                }
                else -> {}
            }
        }
    }
}