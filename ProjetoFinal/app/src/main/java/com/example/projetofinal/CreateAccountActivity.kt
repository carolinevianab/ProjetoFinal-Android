package com.example.projetofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetofinal.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}