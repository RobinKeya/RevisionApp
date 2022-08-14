package com.example.revisionapp.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import com.example.revisionapp.databinding.FragmentPastPapersBinding
import com.example.revisionapp.helper.Constant
import com.example.revisionapp.helper.PrefHelper
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val REQUEST_CODE_IMAGE_PICK =0
class PastPapersFragment : Fragment() {
    private lateinit var _binding:FragmentPastPapersBinding
    private val binding get()=_binding!!
    private lateinit var prefHelper: PrefHelper
    var currentUri : Uri? =null
    val imageRef =Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (prefHelper.getString(Constant.PREF_NAME)!="admin"){
            binding.fab.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPastPapersBinding.inflate(inflater,container, false)
        prefHelper = PrefHelper(requireContext())
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.fab.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                requireActivity().startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }
    }
    private fun uploadImageToStorage(filename:String)= CoroutineScope(Dispatchers.IO).launch{
        try {
            currentUri?.let {
                imageRef.child("images/$filename").putFile(it)
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),"Added",Toast.LENGTH_LONG).show()
                }
            }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==Activity.RESULT_OK && resultCode == REQUEST_CODE_IMAGE_PICK){
            data?.data?.let {
                currentUri = it
            }
        }
    }

}