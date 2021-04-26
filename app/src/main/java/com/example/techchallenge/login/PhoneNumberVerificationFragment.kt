package com.example.techchallenge.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.techchallenge.MainViewModel
import com.example.techchallenge.R
import com.example.techchallenge.data.Resource
import com.example.techchallenge.databinding.FragmentPhoneNumerVerificationBinding
import com.example.techchallenge.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass.
 * Use the [PhoneNumberVerificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PhoneNumberVerificationFragment : Fragment(R.layout.fragment_phone_numer_verification) {

    private val viewModel by activityViewModels<MainViewModel>()
    private val binding by viewBinding(FragmentPhoneNumerVerificationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextPhone.setText("+919876543212")

        binding.button.setOnClickListener {
            val number = binding.editTextPhone.text
            if (number.length < 10) {
                Toast.makeText(context, "Enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.verifyPhoneNumber(number.toString())
        }

        viewModel.verifyPhoneNumber.onEach {

            binding.progress.isVisible = it is Resource.Loading

            when (it) {
                is Resource.Error -> Log.d(TAG, "Error: ${it.message}")
                is Resource.Loading -> Log.d(TAG, "Loading: ")
                is Resource.Success -> findNavController().navigate(
                    PhoneNumberVerificationFragmentDirections.actionPhoneNumberVerificationFragmentToOtpVerificationFragment(
                        phoneNumber = binding.editTextPhone.text.toString()
                    )
                )
            }
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val TAG = "PhoneNumberVerification"
    }
}