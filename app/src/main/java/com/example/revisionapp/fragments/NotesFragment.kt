package com.example.revisionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.revisionapp.R
import com.example.revisionapp.adapters.NotesAdapter
import com.example.revisionapp.databinding.FragmentNotesBinding
import com.example.revisionapp.helper.Constant
import com.example.revisionapp.helper.PrefHelper
import com.example.revisionapp.models.Note
import com.google.firebase.firestore.FirebaseFirestore

class NotesFragment : Fragment(),NotesAdapter.ItemClickListener {

    private var _binding: FragmentNotesBinding? = null
    private lateinit var prefHelper: PrefHelper
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        prefHelper = PrefHelper(requireContext())
        initViews()
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        if(prefHelper.getString(Constant.PREF_NAME)!="admin"){
            binding.fab.visibility =View.GONE
        }

    }

    private fun initViews() {
        binding.apply {
            val notesAdapter = NotesAdapter(requireContext(),this@NotesFragment)
            notesRecyclerView.adapter = notesAdapter
            val db = FirebaseFirestore.getInstance()
            val notesReference = db.collection("notes")
            notesReference
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result.let {
                            val notesList = it.documents
                                .map { snapShot ->
                                    snapShot.toObject(Note::class.java)
                                }.sortedByDescending { note ->
                                    note?.timeStamp
                                }.filterNotNull()
                                .toMutableList()
                            with(notesAdapter) {
                                addNotes(notesList)
                            }

                        }
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "This was not Successful. Try Again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }.addOnFailureListener { e ->
                    Toast.makeText(requireActivity(), e.localizedMessage, Toast.LENGTH_LONG).show()

                }
        }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun itemClicked(view: View, note: Note) {
       if (view.id == R.id.noteCardView) {
           findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
      }
    }
}