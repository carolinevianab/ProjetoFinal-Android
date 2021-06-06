package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetofinal.databinding.ActivityFinishPurchaseBinding

class FinishPurchaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityFinishPurchaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFinishPurchase.setOnClickListener {
            val intent = Intent(this, FinishedPurchaseActivity::class.java)
            startActivity(intent)
        }

        binding.backButtonPurchase.setOnClickListener {
            finish()
        }
    }
}