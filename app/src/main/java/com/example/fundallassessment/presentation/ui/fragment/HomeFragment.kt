package com.example.fundallassessment.presentation.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentHomeBinding
import com.example.fundallassessment.presentation.ui.activities.AuthenticationActivity
import com.example.fundallassessment.presentation.viewModel.FundAllExpensesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val fundallViewModel by activityViewModels<FundAllExpensesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        fundallViewModel.getUserDetailsFromSharedPref()
        binding.apply {
            viewModel = fundallViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?") // Specifying a listener allows you to take an action before dismissing the dialog.
            .setIcon(R.drawable.fundall_icon)
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton(
                android.R.string.yes,
                DialogInterface.OnClickListener { _, _ ->
                    // Continue with delete operation
                    fundallViewModel.removeUserDetailsFromSharedPref()
                    requireActivity().finish()
                    val intent = Intent(requireActivity(), AuthenticationActivity::class.java)
                    startActivity(intent)
                }) // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton(android.R.string.no, null)
            .show()
    }
}