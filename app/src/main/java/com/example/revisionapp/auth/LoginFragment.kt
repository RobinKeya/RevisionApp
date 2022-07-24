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
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        initViews()
        auth = FirebaseAuth.getInstance()
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
                //saveUser
                toHome()
            }else{
                Toast.makeText(requireContext(),"Please verify your email first",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun toHome() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
    }

    private fun isEmailValid(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}