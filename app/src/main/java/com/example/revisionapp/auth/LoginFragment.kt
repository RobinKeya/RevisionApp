package com.example.revisionapp.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.revisionapp.databinding.FragmentLoginBinding
import com.example.revisionapp.models.User
import com.example.revisionapp.helper.Constant
import com.example.revisionapp.helper.PrefHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var prefHelper: PrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        prefHelper = PrefHelper(requireContext())
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.toSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        binding.loginBtn.setOnClickListener { authenticate() }
    }

    private fun authenticate() {
        val email = binding.emailEdt.editText?.text.toString()
        val password = binding.passwordEdt.editText?.text.toString()
        if(password.isNotBlank() && isEmailValid(email)){
            signInUser(email, password)
        }else{
            Toast.makeText(requireContext(),"All fields must be filled",Toast.LENGTH_LONG).show()
        }
    }

    private fun signInUser(email:String,password:String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
            val user = auth.currentUser!!
            if (user.isEmailVerified){
                //save user in shared Preferences and move to home.
                val uid = user.uid
                getUser(uid)
                toHome()
            }else{
                Toast.makeText(requireContext(),"Please verify your email first",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getUser(uid: String) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)
        userRef.get().addOnSuccessListener {documentSnapshot->
            if(documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                if(user !=null){
                    user.name?.let { prefHelper.put(Constant.PREF_NAME, it) }
                    user.email?.let { prefHelper.put(Constant.PREF_EMAIL, it) }
                    prefHelper.put(Constant.PREF_IS_LOGIN,true)
                }

            }
        }
    }

    private fun toHome() {
        if(prefHelper.getString(Constant.PREF_NAME)=="admin"){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAdminFragment())
        }
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun isEmailValid(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}