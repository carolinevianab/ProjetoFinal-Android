package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetofinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            var createAccount = Intent(this, CreateAccountActivity::class.java)
            startActivity(createAccount)
        }

        binding.btnLogin.setOnClickListener {
            var login = Intent(this, HomeActivity::class.java)
            startActivity(login)
        }
    }
}