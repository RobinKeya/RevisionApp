package com.example.revisionapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.revisionapp.databinding.FragmentHomeBinding
import com.example.revisionapp.helper.Constant
import com.example.revisionapp.helper.PrefHelper
import com.example.revisionapp.viewModels.HomeViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var prefHelper: PrefHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        prefHelper = PrefHelper(requireContext())
        initViews()

        viewModel.value.observe(viewLifecycleOwner, { value->
            when(value){
                0-> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotesFragment())
                    viewModel.navigationComplete()
                }
                1-> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPastPapersFragment())
                    viewModel.navigationComplete()
                }
                2-> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSupportFragment())
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
    private fun initViews() {
        binding.apply {
            profileName.text= prefHelper.getString(Constant.PREF_NAME)
            profileEmail.text = prefHelper.getString(Constant.PREF_EMAIL)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}