package com.example.revisionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.revisionapp.R
import com.example.revisionapp.databinding.FragmentAddNoteBinding
import com.example.revisionapp.models.Note
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AddNoteFragment : Fragment() {
    private lateinit var _binding: FragmentAddNoteBinding
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteBinding.inflate(inflater,container,false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btSave.setOnClickListener {
            validateUserInputs()
        }
    }

    private fun validateUserInputs() {
        val title = binding.addNoteTitle.editText?.text.toString().trim()
        val noteBody = binding.addNoteDetail.editText?.text.toString().trim()

        if (title.isNotEmpty()) {

            if (noteBody.isNotEmpty()) {
                saveNote(title, noteBody)
            } else {
                binding.addNoteDetail.error = "Cannot be empty"
            }
        } else {
            binding.addNoteTitle.error = "Cannot be empty"
        }
    }

    private fun saveNote(title: String, noteBody: String) {
        val db = FirebaseFirestore.getInstance()
        val noteReference=db.collection("notes")
        val currentDate =
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(System.currentTimeMillis())
        val currentTime =
            SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(System.currentTimeMillis())
        val currentDateTime = "$currentDate $currentTime"
        val noteId =noteReference.id
        val note = Note(noteId, title, currentDateTime, noteBody)
        noteReference.document("$noteId").set(note).addOnCompleteListener {
            Toast.makeText(requireContext(),"Saved", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { e->
            Toast.makeText(requireContext(),e.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

}