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
import androidx.navigation.fragment.navArgs
import com.example.techchallenge.MainViewModel
import com.example.techchallenge.R
import com.example.techchallenge.data.Resource
import com.example.techchallenge.databinding.FragmentOtpVerificationragmentBinding
import com.example.techchallenge.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass.
 * Use the [OtpVerificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class OtpVerificationFragment : Fragment(R.layout.fragment_otp_verificationragment) {

    private val viewModel by activityViewModels<MainViewModel>()
    private val binding by viewBinding(FragmentOtpVerificationragmentBinding::bind)
    private val args by navArgs<OtpVerificationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            textView10.text = args.phoneNumber
            val otp = "1234"
            editTextNumber.setText(otp)

            button2.setOnClickListener {
                if (editTextNumber.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Enter a valid otp", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.verifyOtp(args.phoneNumber, otp)
            }
        }

        viewModel.verifyOtp.onEach {

            binding.progress.isVisible = it is Resource.Loading

            when (it) {
                is Resource.Error -> Log.d(TAG, "Error: ${it.message}")
                is Resource.Loading -> Log.d(TAG, "Loading: ")
                is Resource.Success -> findNavController().navigate(
                    OtpVerificationFragmentDirections.actionOtpVerificationFragmentToHomeFragment(it.data?.token!!)
                )
            }
        }.launchIn(lifecycleScope)

    }

    companion object {
        private const val TAG = "OtpVerificationFragment"
    }
}