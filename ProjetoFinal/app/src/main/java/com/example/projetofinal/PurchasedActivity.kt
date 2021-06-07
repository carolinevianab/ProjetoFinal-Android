package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.projetofinal.databinding.ActivityPurchasedBinding
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.model.AllPurchases
import com.example.projetofinal.model.BooksBought
import com.example.projetofinal.model.Produto
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchasedActivity : AppCompatActivity() {
    lateinit var binding: ActivityPurchasedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchasedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.profBackButton.setOnClickListener {
            finish()
        }

        val extra = intent.getStringExtra("compra")

        val user = getUser()
        val databese = FirebaseDatabase.getInstance().reference.child("Compras").child("Users")
                .child(user!!.uid).child("Compra").child(extra!!).child("Books")

        val retrofit = Retrofit.Builder()
                .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(ProductService::class.java)

        val call = service.getAllBooks("${user!!.uid}/Compra/${extra!!}/Books")

        val callback = object : Callback<List<BooksBought>> {
            override fun onResponse(call: Call<List<BooksBought>>, response: Response<List<BooksBought>>) {
                if (response.isSuccessful) {
                    val productsList = response.body()
                    updateUI(productsList)
                }
                else {
                    Snackbar.make(
                            binding.purchasesContainer,
                            "Não foi possível conectar-se ao servidor.",
                            Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<List<BooksBought>>, t: Throwable) {
                Snackbar.make(
                        binding.purchasesContainer,
                        "Não foi possível atualizar os produtos.",
                        Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha na conexão", t)
            }
        }
        call.enqueue(callback)

    }

    fun updateUI(list: List<BooksBought>?){
        if (list == null){
            return
        }

        list.forEach {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ProductService::class.java)

            val call = service.getBookInfo(it.bookId.toString())

            val callback = object : Callback<Produto> {
                override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                    if (response.isSuccessful) {
                        val productsList = response.body()
                        updateUI2(productsList)
                    }
                    else {
                        Snackbar.make(
                            binding.purchasesContainer,
                            "Não foi possível conectar-se ao servidor.",
                            Snackbar.LENGTH_LONG
                        ).show()

                        Log.e("ERRO-Retrofit", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Produto>, t: Throwable) {
                    Snackbar.make(
                        binding.purchasesContainer,
                        "Não foi possível atualizar os produtos.",
                        Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", "Falha na conexão", t)
                }
            }
            call.enqueue(callback)

        }


    }

    fun updateUI2(book: Produto?){
        if (book == null){
            return
        }
        val card = CardItemBinding.inflate(layoutInflater)
        card.cardProductTitle.text = book.Titulo
        val price = String.format("%.2f",book.preco)
        val priceDiscount = String.format("%.2f", book.preco - book.desconto)
        card.cardProductPrice.text = "De ${price} por ${priceDiscount}"
        card.cardBookId.visibility = View.INVISIBLE
        Picasso.get().load(book.Capa).into(card.cardProductImage)

        binding.purchasesContainer.addView(card.root)
    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

}