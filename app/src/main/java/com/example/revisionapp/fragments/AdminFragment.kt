package com.example.revisionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.revisionapp.R
import com.example.revisionapp.databinding.FragmentAdminBinding
import com.example.revisionapp.helper.PrefHelper
import com.example.revisionapp.viewModels.HomeViewModel

class AdminFragment : Fragment() {

    private lateinit var _binding: FragmentAdminBinding
    private val binding get() = _binding !!
    private lateinit var viewModel: HomeViewModel
    private lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentAdminBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        prefHelper = PrefHelper(requireContext())
        viewModel.value.observe(viewLifecycleOwner, { value->
            when(value){
                0-> {
                    findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToNotesFragment())
                    viewModel.navigationComplete()
                }
                1-> {
                    findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToPastPapersFragment())
                    viewModel.navigationComplete()
                }
                2-> {
                    findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToAssignmentFragment())
                    viewModel.navigationComplete()
                }
                3-> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToForumFragment())
                    viewModel.navigationComplete()
                }
                4-> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
                    viewModel.navigationComplete()
                }
                5-> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
                    viewModel.navigationComplete()
                }
            }
        })
        return binding.root
    }

}