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
import retrofit2.*
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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProductService::class.java)

        val call = service.getAllBooks("${user!!.uid}/Compra/${extra!!}/Books")

        val callback = object : Callback<List<BooksBought>> {
            override fun onFailure(call: Call<List<BooksBought>>, t: Throwable) {
                Snackbar.make(
                    binding.purchasesContainer,
                    "Não foi possível atualizar os produtos.",
                    Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha na conexão", t)
            }

            override fun onResponse(
                call: Call<List<BooksBought>>,
                response: Response<List<BooksBought>>
            ) {
                if (response.isSuccessful){
                    val productList = response.body()
                    Log.e("teste", productList.toString())
                    getBooksFromPurchase(productList)
                }
            }

        }

        call.enqueue(callback)


    }

    fun getBooksFromPurchase(purchase: List<BooksBought>?){
        purchase?.forEach {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ProductService::class.java)

            val call = service.getBookInfo(it.BookId.toString())

            val callback = object : Callback<Produto> {
                override fun onFailure(call: Call<Produto>, t: Throwable) {
                    Snackbar.make(
                        binding.purchasesContainer,
                        "Não foi possível atualizar os produtos.",
                        Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", "Falha na conexão", t)
                }

                override fun onResponse(
                    call: Call<Produto>,
                    response: Response<Produto>
                ) {
                    if (response.isSuccessful){
                        val product = response.body()
                        updateUI(product)
                    }
                }

            }

            call.enqueue(callback)

        }
    }



    fun updateUI(book: Produto?){
        if (book == null){
            return
        }
        val card = CardItemBinding.inflate(layoutInflater)
        card.cardProductTitle.text = book.Titulo
        val price = String.format("%.2f",book.preco)
        val priceDiscount = String.format("%.2f", book.preco - book.desconto)
        card.cardProductPrice.text = "${priceDiscount}"
        card.cardBookId.visibility = View.INVISIBLE
        Picasso.get().load(book.Capa).into(card.cardProductImage)

        binding.purchasesContainer.addView(card.root)
    }



    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

}