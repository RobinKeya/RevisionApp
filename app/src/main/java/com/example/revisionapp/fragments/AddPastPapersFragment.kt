package com.example.revisionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.revisionapp.R


class AddPastPapersFragment : Fragment() {
    private lateinit var _binding: com.example.revisionapp.databinding.FragmentAddPastPapersBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPastPapersBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

}