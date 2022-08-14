package com.example.revisionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.revisionapp.R
import com.example.revisionapp.databinding.FragmentAssignmentBinding
import com.example.revisionapp.helper.Constant
import com.example.revisionapp.helper.PrefHelper

class AssignmentFragment : Fragment() {
    private var _binding: FragmentAssignmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAssignmentBinding.inflate(inflater)
        prefHelper = PrefHelper(requireContext())
        return inflater.inflate(R.layout.fragment_assignment, container, false)
    }

    override fun onStart() {
        super.onStart()
        if(prefHelper.getString(Constant.PREF_NAME)!="admin"){
            binding.fab.visibility =View.GONE
        }
    }

}