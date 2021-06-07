package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.projetofinal.databinding.ActivityAllProductsBinding
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.model.Produto
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllProductsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllProductsBinding
    val categories = arrayOf("Ficção", "Fantasia", "Autoajuda", "Suspense", "Outros")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonAllProducts.setOnClickListener {
            finish()
        }
        var rq = intent.getStringExtra("requestType")
        if (rq == null) { rq = "All" }
        getFromDatabase(rq)
    }

    fun getFromDatabase(requestType: String){

        if (binding.allProductsContainer.childCount == 0) {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ProductService::class.java)

            val call = service.listProducts()

            val callback = object : Callback<List<Produto>> {
                override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                    if (response.isSuccessful) {
                        val productsList = response.body()
                        updateUI(productsList, requestType)
                    }
                    else {
                        Snackbar.make(
                            binding.allProductsContainer,
                            "Não foi possível conectar-se ao servidor.",
                            Snackbar.LENGTH_LONG
                        ).show()

                        Log.e("ERRO-Retrofit", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                    Snackbar.make(
                        binding.allProductsContainer,
                        "Não foi possível atualizar os produtos.",
                        Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", "Falha na conexão", t)
                }
            }
            call.enqueue(callback)

        }
    }

    fun updateUI(productList: List<Produto>?, category: String){
        if (category == "All") {
            productList?.forEach {
                setCard(it)
            }
        }
        else{
            productList?.forEach {
                if (it.Categoria == category && categories.contains(category)){
                    setCard(it)
                }
                else if (it.Titulo.contains(category, ignoreCase = true) || it.Autor.contains(category, ignoreCase = true)) {
                    setCard(it)
                }
            }
        }

    }

    fun setCard(p: Produto){
        val card = CardItemBinding.inflate(layoutInflater)
        card.cardProductTitle.text = p.Titulo
        val price = String.format("%.2f",p.preco)
        val priceDiscount = String.format("%.2f", p.preco - p.desconto)
        card.cardProductPrice.text = "De ${price} por ${priceDiscount}"
        Picasso.get().load(p.Capa).into(card.cardProductImage)
        card.cardBookId.visibility = View.INVISIBLE

        val productInfo = Intent(this, ProductActivity::class.java)
        productInfo.putExtra("idBook", p.id.toString())
        card.cardProductTitle.setOnClickListener {
            startActivity(productInfo)
        }
        card.cardProductImage.setOnClickListener {
            startActivity(productInfo)
        }
        card.cardProductPrice.setOnClickListener {
            startActivity(productInfo)
        }
        binding.allProductsContainer.addView(card.root)
    }
}