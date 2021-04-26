package com.example.techchallenge.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.techchallenge.MainViewModel
import com.example.techchallenge.R
import com.example.techchallenge.data.Resource
import com.example.techchallenge.databinding.FragmentDiscoverBinding
import com.example.techchallenge.util.viewBinding


class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel by activityViewModels<MainViewModel>()
    private val binding by viewBinding(FragmentDiscoverBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.getProfile(it["TOKEN"] as String)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> Log.d(TAG, "Error: ${it.message}")
                is Resource.Loading -> Log.d(TAG, "Loading: ")
                is Resource.Success -> {
                    Log.d(TAG, "Success: ${it.data}")

                    val profile = it.data

                    with(binding) {
                        name.text =
                                "${profile?.invites?.profiles?.get(0)?.general_information?.first_name}, ${
                                    profile?.invites?.profiles?.get(0)?.general_information?.age
                                }"

                        Glide.with(this@DiscoverFragment)
                                .load(profile?.invites?.profiles?.get(0)?.photos?.get(0)?.photo)
                                .placeholder(R.drawable.ic_baseline_people_alt_24)
                                .into(pic)


                        Glide.with(this@DiscoverFragment)
                                .load(profile?.likes?.profiles?.get(0)?.avatar)
                                .placeholder(R.drawable.ic_baseline_people_alt_24)
                                .into(image1)

                        Glide.with(this@DiscoverFragment)
                                .load(profile?.likes?.profiles?.get(1)?.avatar)
                                .placeholder(R.drawable.ic_baseline_people_alt_24)
                                .into(image2)

                    }
                }
            }
        }


    }

    companion object {
        private const val TAG = "DiscoverFragment"
        fun getInstance(token: String) = DiscoverFragment().also {
            it.arguments = bundleOf("TOKEN" to token)
        }
    }
}