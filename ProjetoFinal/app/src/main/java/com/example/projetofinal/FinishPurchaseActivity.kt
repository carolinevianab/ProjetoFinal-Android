package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.projetofinal.DB.AppDataBase
import com.example.projetofinal.databinding.ActivityFinishPurchaseBinding

class FinishPurchaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityFinishPurchaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardHome.isChecked = true

        binding.btnFinishPurchase.setOnClickListener {
            Thread{
                val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
                db.Produto_carrinhoDAO().cleanCart()
                runOnUiThread {
                    val intent = Intent(this, FinishedPurchaseActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }

        binding.backButtonPurchase.setOnClickListener {
            finish()
        }
    }
}