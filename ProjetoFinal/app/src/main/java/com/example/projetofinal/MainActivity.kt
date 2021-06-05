package com.example.projetofinal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projetofinal.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (getUser() == null) {
            binding.btnLogin.setOnClickListener {
                val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
                val intent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build()

                // Apparently, startActivityForResult is deprecated
                // Now, this is the new way
                startForResult.launch(intent)
            }
        }
        else {
            goToHome()
        }
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data: Intent? = result.data
            // Faz algo
            goToHome()
        }

    }

    fun goToHome(){
        val login = Intent(this, HomeActivity::class.java)
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(login)
        finish()
    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}