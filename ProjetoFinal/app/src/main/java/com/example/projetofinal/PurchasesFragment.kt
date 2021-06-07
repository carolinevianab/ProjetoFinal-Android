package com.example.projetofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.databinding.FragmentPurchasesBinding
import com.example.projetofinal.model.AllPurchases
import com.example.projetofinal.model.Produto
import com.example.projetofinal.model.Purchase
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PurchasesFragment : Fragment() {
    lateinit var binding: FragmentPurchasesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPurchasesBinding.inflate(inflater)
        /*

        val retrofit = Retrofit.Builder()
            .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProductService::class.java)

        val call = service.getAllPurchase(getUser()!!.uid)

        val callback = object : Callback<List<Purchase>> {
            override fun onResponse(call: Call<List<Purchase>>, response: Response<List<Purchase>>) {
                if (response.isSuccessful) {
                    val productsList = response.body()
                    updateList(productsList)
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

            override fun onFailure(call: Call<List<Purchase>>, t: Throwable) {
                Snackbar.make(
                    binding.purchasesContainer,
                    "Não foi possível atualizar os produtos.",
                    Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha na conexão", t)
            }
        }
        call.enqueue(callback)

         */

        return binding.root
    }
    /*

    fun updateList(list: List<Purchase>?){
        list?.forEach {purchase ->
            val compra = CardItemBinding.inflate(layoutInflater)
            compra.cardProductImage.setOnClickListener {
                val intent = Intent(this.activity, PurchasedActivity::class.java)
                intent.putExtra("compra", purchase.purId)
                startActivity(intent)
            }
            binding.purchasesContainer.addView(compra.root)
        }

    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

     */

}