package com.example.techchallenge.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.navArgs
import com.example.techchallenge.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val args by navArgs<HomeFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.commit {
            add(R.id.root, DiscoverFragment.getInstance(token = args.token))
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}