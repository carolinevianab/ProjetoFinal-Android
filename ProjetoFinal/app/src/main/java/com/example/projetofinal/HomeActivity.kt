package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.projetofinal.databinding.ActivityHomeBinding
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.model.Produto
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val homeFrag = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, homeFrag)
            .commit()



        val w = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.topAppBar,R.string.app_name,R.string.app_name
        )

        binding.drawerLayout.addDrawerListener(w)
        w.syncState()
        binding.navigationView.setCheckedItem(R.id.itemHome)
        binding.navigationView.setNavigationItemSelectedListener{menuItem ->
            when (menuItem.itemId) {
                R.id.itemHome -> {
                    val homeFrag = HomeFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.mainContainer, homeFrag)
                            .commit()
                }
                R.id.itemCart -> {
                    val cartFrag = CartFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, cartFrag)
                        .commit()
                }
                R.id.itemCategory -> {
                    val catFrag = CategoryFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, catFrag)
                        .commit()
                }
                R.id.itemPurchases -> {
                    val purFrag = PurchasesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, purFrag)
                        .commit()
                }

            }
            binding.drawerLayout.closeDrawer(binding.navigationView)
            true

        }




    }

    override fun onResume() {
        super.onResume()

        if (homeFrag.binding.offersContainer.childCount == 0) {

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
                        updateUI(productsList)
                    }
                    else {
                        Snackbar.make(
                                homeFrag.binding.offersContainer,
                                "Não foi possível conectar-se ao servidor.",
                                Snackbar.LENGTH_LONG
                        ).show()

                        Log.e("ERRO-Retrofit", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                    Snackbar.make(
                            homeFrag.binding.offersContainer,
                            "Não foi possível atualizar os produtos.",
                            Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", "Falha na conexão", t)
                }
            }
            call.enqueue(callback)

        }


    }

    fun updateUI(productList: List<Produto>?){
        // Vai precisar fazer o foreach aqui

        productList?.forEach {
            val card = CardItemBinding.inflate(layoutInflater)
            card.cardProductTitle.text = it.Titulo
            card.cardProductPrice.text = it.preco.toString()
            card.cardBookId.text = it.id.toString()
            homeFrag.setIntent(card)
        }

    }



}