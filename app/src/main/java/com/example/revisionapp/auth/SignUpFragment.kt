package com.example.revisionapp.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.revisionapp.databinding.FragmentSignUpBinding
import com.example.revisionapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private var _binding :FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        initViews()
        auth = Firebase.auth
        return binding.root
    }

    private fun initViews() {
        binding.toLogin.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
        binding.signUpBtn.setOnClickListener { confirmDetails() }
    }

    private fun confirmDetails() {
        val name = binding.nameEdt.editText?.text.toString()
        val email = binding.emailEdt.editText?.text.toString()
        val phone = binding.phoneEdt.editText?.text.toString()
        val password = binding.passwordEdt.editText?.text.toString()
        val confirmPassword = binding.confirmPasswordEdt.editText?.text.toString()

        if(name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && password.isNotBlank()
            && confirmPassword.isNotBlank()){
            if (isEmailValid(email)){
                if (passwordMatch(password,confirmPassword)){
                    signUpUser(name, email, phone, password)
                }
            }else{
                Toast.makeText(requireContext(),"Invalid Email",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_LONG).show()
        }
    }

    private fun signUpUser(name: String, email: String, phone: String, password: String) {
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    sendVerificationEmail()
                    saveUser(name,email,phone)
                }
                .addOnFailureListener { e->
                    Toast.makeText(requireContext(),e.localizedMessage,Toast.LENGTH_LONG).show()
                }
    }

    private fun saveUser(name: String,email:String,phone: String) {
        val db = FirebaseFirestore.getInstance()
        val userReference = db.collection("user")
        val uid = if (auth.currentUser != null){ auth.currentUser!!.uid }else null
        val user = User(uid, name, email, phone)

        userReference.document(uid!!).set(user).addOnCompleteListener { task->
            if (task.isSuccessful) {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
            }else{
                Toast.makeText(requireContext(),"Failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e->
            Toast.makeText(requireContext(),e.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }
    private fun sendVerificationEmail(){
        val user =auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task->
            if(task.isSuccessful){
                //Todo -> Implicit intent to gmail or other mailing services.
                Toast.makeText(requireContext(),"Please verify your email then login",
                    Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "Failed to send Verification link",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isEmailValid(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun passwordMatch(password:String,confirmPassword:String):Boolean{
        return password ==confirmPassword
    }
}