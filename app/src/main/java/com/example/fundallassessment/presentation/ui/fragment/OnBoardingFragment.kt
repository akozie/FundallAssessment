package com.example.fundallassessment.presentation.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fundallassessment.R
import com.example.fundallassessment.databinding.FragmentOnBoardingBinding
import com.example.fundallassessment.presentation.viewModel.FundAllExpensesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private val viewModel by activityViewModels<FundAllExpensesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUserDetailsFromSharedPref()?.let {
            findNavController().navigate(R.id.logInFragment)
        }

        binding.startOnboarding.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment2)
        }
    }
}