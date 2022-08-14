package com.example.revisionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.revisionapp.R
import com.example.revisionapp.databinding.FragmentProfileBinding
import com.example.revisionapp.helper.Constant
import com.example.revisionapp.helper.PrefHelper

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding?=null
    private val binding get() = _binding!!
    private lateinit var prefHelper: PrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater)
        prefHelper = PrefHelper(requireContext())
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.profileName.text = prefHelper.getString(Constant.PREF_NAME)
        binding.profileEmail.text = prefHelper.getString(Constant.PREF_EMAIL)
        binding.logoutText.setOnClickListener { signOut() }
        binding.contactUsText.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSupportFragment())
        }

    }

    private fun signOut() {
        prefHelper.clear()
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }
}