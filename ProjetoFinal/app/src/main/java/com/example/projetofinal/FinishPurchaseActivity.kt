package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.projetofinal.DB.AppDataBase
import com.example.projetofinal.databinding.ActivityFinishPurchaseBinding
import com.example.projetofinal.model.BooksBought
import com.example.projetofinal.model.Produto_carrinho
import com.example.projetofinal.model.Purchase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

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
                val cartProducts = db.Produto_carrinhoDAO().listAll()
                db.Produto_carrinhoDAO().cleanCart()
                runOnUiThread {
                    savePurchaseToDatabase(cartProducts)
                    val intent = Intent(this, FinishedPurchaseActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }

        binding.backButtonPurchase.setOnClickListener {
            finish()
        }
    }

    fun savePurchaseToDatabase(productList: List<Produto_carrinho>){
        val user = getUser()

        if (user != null) {
            val databese = FirebaseDatabase.getInstance().reference.child("Compras").child("Users")
                .child(user.uid)

            val novoNo = databese.child("Compra").push()

            val purchasee = Purchase(purId = novoNo.key.toString())
            novoNo.setValue(purchasee)

            //val newPath = novoNo.child(purchasee.purId)
            var count = 0
            productList.forEach {
                val book = BooksBought(bookId = it.id, qtd = it.qtde)
                novoNo.child("Books").child(count.toString()).push().setValue(book)
                count += 1
            }


        }
    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

}