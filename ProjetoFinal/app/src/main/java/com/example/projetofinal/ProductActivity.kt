package com.example.projetofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.projetofinal.databinding.ActivityProductBinding
import com.example.projetofinal.model.Produto
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val book = intent.getStringExtra("idBook")
        if (book != null) {
            getBookInfo(book)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    fun getBookInfo(bookId: String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProductService::class.java)

        val call = service.getBookInfo(bookId)

        val callback = object : Callback<Produto> {
            override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                binding.progressDetails.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    val product = response.body()
                    updateUI(product)
                }
                else {
                    Snackbar.make(
                        binding.txtDescriptionDetail,
                        "Não foi possível conectar-se ao servidor.",
                        Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Produto>, t: Throwable) {
                binding.progressDetails.visibility = View.INVISIBLE
                Snackbar.make(
                    binding.txtDescriptionDetail,
                    "Não foi possível atualizar os produtos.",
                    Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha na conexão", t)
            }
        }
        call.enqueue(callback)
        binding.progressDetails.visibility = View.VISIBLE
    }

    fun updateUI(info: Produto?){
        if (info != null) {
            binding.txtTitleProductDetail.text = info.Titulo
            binding.txtDescriptionDetail.text = info.Descricao
        }
    }
}