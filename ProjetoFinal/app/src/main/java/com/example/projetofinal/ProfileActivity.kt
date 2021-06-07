package com.example.projetofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projetofinal.databinding.ActivityProfileBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profBackButton.setOnClickListener {
            finish()
        }

        val user = getUser()
        if (user == null) {
            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()


            startActivity(intent)
        }
        else {
            val text = """
                |Nome: ${user.displayName.toString()}
                |E-mail: ${user.email.toString()} """".trimMargin("|")
            binding.txtUserData.text = text
        }

        binding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}