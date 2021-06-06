package com.example.projetofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetofinal.databinding.ActivityProductBinding


class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

    }
}