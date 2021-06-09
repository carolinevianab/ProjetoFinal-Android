package com.example.projetofinal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
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
        binding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }

        val user = getUser()
        if (user == null) {
            val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                if (result.resultCode == Activity.RESULT_OK){
                    val data: Intent? = result.data
                    updateUser(getUser()!!)
                }

            }

            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            val intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()

            startForResult.launch(intent)
        }
        else {
            updateUser(user)
        }


    }

    fun updateUser(user: FirebaseUser){
        val text = """
                |${getString(R.string.name)}: ${user.displayName.toString()}
                |${getString(R.string.Email)}: ${user.email.toString()} """.trimMargin("|")
        binding.txtUserData.text = text
    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}