package com.example.fundallassessment.presentation.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fundallassessment.R
import com.example.fundallassessment.data.remote.dto.RegisterUserDTO
import com.example.fundallassessment.databinding.FragmentSignUpBinding
import com.example.fundallassessment.presentation.viewModel.FundAllExpensesViewModel
import com.example.fundallassessment.presentation.viewState.observeLiveData
import com.example.fundallassessment.utils.AppConstants.alertDialog
import com.example.fundallassessment.utils.AppConstants.showToast
import com.example.fundallassessment.utils.AppConstants.togglePasswordVisibility
import com.example.fundallassessment.utils.AppConstants.validatePasswordMismatch
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private var isPasswordVisible = false
    private val viewModel by activityViewModels<FundAllExpensesViewModel>()
    private lateinit var dialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = alertDialog(requireContext())
        privacySpannableText()
        binding.passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.password, isPasswordVisible)
        }
        binding.confirmPasswordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.confirmPassword, isPasswordVisible)
        }
        binding.logIn.setOnClickListener {
            findNavController().navigate(R.id.logInFragment)
        }

        binding.cancel.setOnClickListener {
            findNavController().navigate(R.id.onBoardingFragment5)
        }

        binding.registerButton.setOnClickListener {
            signUp()
        }

        binding.benefits.setOnClickListener {
            benefits()
        }

    }

    private fun signUp() {
        when {
            binding.firstName.text.toString().isEmpty() -> {
                binding.firstName.error =
                    getString(R.string.all_please_enter_first_name)
            }
            binding.lastName.text.toString().isEmpty() -> {
                binding.lastName.error =
                    getString(R.string.all_please_enter_last_name)
            }
            binding.emailAddress.text.toString().isEmpty() -> {
                binding.emailAddress.error =
                    getString(R.string.all_please_enter_email_address)
            }
            binding.phoneNumber.text.toString().isEmpty() -> {
                showToast(getString(R.string.all_please_enter_phone_number))
            }
            binding.password.text.toString().isEmpty() -> {
                showToast(getString(R.string.all_please_enter_password))
            }
            binding.confirmPassword.text.toString().isEmpty() -> {
                showToast(getString(R.string.confirm_password))
            }
            !validatePasswordMismatch(
                binding.password.text.toString().trim(),
                binding.confirmPassword.text.toString().trim()
            ) -> {
                showToast("Password mismatch")
            }
            binding.confirmPassword.text.toString().trim().isEmpty() -> {
                binding.password.error = ""
            }
            else -> {
                if (validateSignUpFieldsOnTextChange()) {
                    register()
                }
            }
        }
    }

    private fun register() {

        val registerRequest = RegisterUserDTO(
            firstname = binding.firstName.text.toString().trim(),
            lastname = binding.lastName.text.toString().trim(),
            email = binding.emailAddress.text.toString().trim(),
            password = binding.password.text.toString().trim(),
            password_confirmation = binding.confirmPassword.text.toString().trim()
        )

        viewModel.register(registerRequest)
        observeLiveData(
            dialog,
            viewModel.registerResponse,
        ) {
            it?.let { showToast(it) }
            findNavController().navigate(R.id.logInFragment)
        }
    }


    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        binding.firstName.doOnTextChanged { _, _, _, _ ->
            when {
                binding.firstName.text.toString().trim().isEmpty() -> {
                    showToast(getString(R.string.all_please_enter_first_name))
                    isValidated = false
                }
                binding.firstName.text.toString().trim().isNotEmpty() -> {
                    binding.firstName.error = ""
                    isValidated = true
                }
                else -> {
                    binding.firstName.error = null
                    isValidated = true
                }
            }
        }
        binding.lastName.doOnTextChanged { _, _, _, _ ->
            when {
                binding.lastName.text.toString().trim().isEmpty() -> {
//                    binding.lastName.error =
//                        getString(R.string.all_please_enter_last_name)
                    showToast(getString(R.string.all_please_enter_last_name))
                    isValidated = false
                }
                binding.lastName.text.toString().trim().isNotEmpty() -> {
                    binding.lastName.error = ""
                    isValidated = true
                }
                else -> {
                    binding.lastName.error = null
                    isValidated = true
                }
            }
        }
        binding.emailAddress.doOnTextChanged { _, _, _, _ ->
            when {
                binding.emailAddress.text.toString().trim().isEmpty() -> {
                    showToast(getString(R.string.all_please_enter_email_address))
                    isValidated = false
                }
                binding.emailAddress.text.toString().trim().isNotEmpty() -> {
                    binding.emailAddress.error = ""
                    isValidated = true
                }
                else -> {
                    binding.emailAddress.error = null
                    isValidated = true
                }
            }
        }
        binding.phoneNumber.doOnTextChanged { _, _, _, _ ->
            when {
                binding.phoneNumber.text.toString().trim().isEmpty() -> {
                    showToast(getString(R.string.all_please_enter_phone_number))
                    isValidated = false
                }
                binding.phoneNumber.text.toString().trim().isNotEmpty() -> {
                    binding.phoneNumber.error = ""
                    isValidated = true
                }
                else -> {
                    binding.phoneNumber.error = null
                    isValidated = true
                }
            }
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            when {
                binding.password.text.toString().trim().isEmpty() -> {
                    binding.password.error =
                        getString(R.string.all_please_enter_password)
                    isValidated = false
                }
                binding.password.text.toString().trim().isEmpty() -> {
                    binding.password.error = ""
                    isValidated = true
                }
                else -> {
                    binding.password.error = null
                    isValidated = true
                }
            }
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            when {
                binding.password.text.toString().trim().isEmpty() -> {
                    binding.password.error =
                        getString(R.string.all_please_enter_password)
                    isValidated = false
                }
                !validatePasswordMismatch(
                    binding.password.text.toString().trim(),
                    binding.confirmPassword.text.toString().trim()
                ) -> {
                    binding.password.error =
                        getString(R.string.all_password_mismatch)
                    isValidated = false
                }
                binding.confirmPassword.text.toString().trim().isEmpty() -> {
                    binding.password.error = ""
                    isValidated = true
                }
                else -> {
                    binding.password.error = null
                    isValidated = true
                }
            }
        }
        return isValidated
    }


    private fun privacySpannableText() {
        val signUpText = "By clicking on Sign up, you agree to our "
        val termsAndConditionsText = "terms & conditions"
        val privacyPolicyText = "privacy policy."

        val spannableString =
            SpannableString("$signUpText$termsAndConditionsText and $privacyPolicyText")

        // Set color for "terms & conditions"
        val termsAndConditionsStartIndex = signUpText.length
        val termsAndConditionsEndIndex =
            termsAndConditionsStartIndex + termsAndConditionsText.length
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorPrimary)),
            termsAndConditionsStartIndex,
            termsAndConditionsEndIndex,
            0
        )

        // Set color for "privacy policy"
        val privacyPolicyStartIndex = termsAndConditionsEndIndex + 5 // Adding 5 for " and "
        val privacyPolicyEndIndex = privacyPolicyStartIndex + privacyPolicyText.length
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.colorPrimary)),
            privacyPolicyStartIndex,
            privacyPolicyEndIndex,
            0
        )

        // Set the text to the TextView
        binding.privacy.text = spannableString
    }

    private fun benefits() {
        AlertDialog.Builder(requireContext())
            .setTitle("Benefits")
            .setMessage("Kindly register to see benefits")
            .setIcon(R.drawable.fundall_icon)
            .show()
    }
}