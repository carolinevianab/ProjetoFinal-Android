package com.example.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.projetofinal.databinding.ActivityHomeBinding
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.model.Produto
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
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
        binding.topAppBar.title = getString(R.string.homeDrawer)

        val search = findViewById<SearchView>(R.id.app_bar_search)
        val context = this
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(context, AllProductsActivity::class.java)
                intent.putExtra("requestType", query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        val w = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.topAppBar,R.string.app_name,R.string.app_name
        )

        binding.drawerLayout.addDrawerListener(w)
        w.syncState()
        binding.navigationView.setCheckedItem(R.id.itemHome)
        binding.navigationView.setNavigationItemSelectedListener{menuItem ->
            when (menuItem.itemId) {
                R.id.itemHome -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.mainContainer, homeFrag)
                            .commit()
                    homeFrag.binding.offersContainer.removeAllViews()
                    getFromDatabase()
                    binding.topAppBar.title = getString(R.string.homeDrawer)
                }
                R.id.itemCart -> {
                    val cartFrag = CartFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, cartFrag)
                        .commit()
                    binding.topAppBar.title = getString(R.string.cartDrawer)
                }
                R.id.itemCategory -> {
                    val catFrag = CategoryFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, catFrag)
                        .commit()
                    binding.topAppBar.title = getString(R.string.categoryDrawer)
                }
                R.id.itemPurchases -> {
                    val purFrag = PurchasesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, purFrag)
                        .commit()
                    binding.topAppBar.title = getString(R.string.myPurchasesDrawer)
                }
                R.id.itemProfile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.itemAbout -> {
                    val aboutFrag = AboutFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainContainer, aboutFrag)
                        .commit()
                    binding.topAppBar.title = getString(R.string.about_this_app)
                }

            }
            binding.drawerLayout.closeDrawer(binding.navigationView)
            true

        }




    }


    override fun onResume() {
        super.onResume()
        getFromDatabase()

    }

    fun getFromDatabase(){
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


        if (productList == null){
            return
        }

        for (i in 0..3) {
            val card = CardItemBinding.inflate(layoutInflater)
            card.cardProductTitle.text = productList[i].Titulo
            val price = String.format("%.2f",productList[i].preco)
            val priceDiscount = String.format("%.2f", productList[i].preco - productList[i].desconto)
            card.cardProductPrice.text = "${getString(R.string.saleOldPrice)} \$ ${price} ${getString(R.string.saleNewPrice)} \$ ${priceDiscount}"
            card.cardBookId.text = productList[i].id.toString()
            card.cardBookId.visibility = View.INVISIBLE
            Picasso.get()
                .load(productList[i].Capa)
                .resize(300, 450)
                .centerCrop()
                .into(card.cardProductImage)
            homeFrag.setIntent(card)
        }

    }



}