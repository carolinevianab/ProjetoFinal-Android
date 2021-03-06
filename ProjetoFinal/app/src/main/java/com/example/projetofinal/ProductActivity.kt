package com.example.projetofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.projetofinal.DB.AppDataBase
import com.example.projetofinal.databinding.ActivityProductBinding
import com.example.projetofinal.model.Produto
import com.example.projetofinal.model.Produto_carrinho
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread


class ProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val book = intent.getStringExtra("idBook")
        if (book != null) {
            getBookInfo(book, 0)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.btnAddToCart.setOnClickListener{
            if (book != null) {
                getBookInfo(book, 1)
            }
        }

        binding.btnReadMore.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.cardDescription)
            if (binding.txtDescriptionDetail.visibility == View.GONE) {
                binding.txtDescriptionDetail.visibility = View.VISIBLE
            }
            else {
                binding.txtDescriptionDetail.visibility = View.GONE
            }
        }
    }

    fun updateProductQuantity(product: Produto_carrinho){
        Thread{
            val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
            db.Produto_carrinhoDAO().update(product)
        }.start()
    }

    fun insertIntoCart(product: Produto_carrinho){
        Thread{
            val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
            db.Produto_carrinhoDAO().insert(product)
        }.start()
    }

    fun updateCart(info: Produto?, book: String){
        Thread{
            val db = Room.databaseBuilder(this, AppDataBase::class.java, "db").build()
            val p = db.Produto_carrinhoDAO().listProduct(book.toInt())
            runOnUiThread {
                if(info != null) {
                    val qnt = binding.editQuantity.text!!
                    if(qnt.isNotBlank() && qnt.toString().toInt() > 0) {
                        if (p == null) { // INSERT; Se n??o existir o produto no carrinho.
                            val id = book.toInt()
                            val title = info.Titulo
                            val price =
                                (String.format("%.2f", info.preco - info.desconto)).toDouble()
                            val img = info.Capa
                            val qnt = binding.editQuantity.text.toString().toInt()

                            val product = Produto_carrinho(id, title, price, qnt, img)

                            insertIntoCart(product)
                            Snackbar.make(binding.btnAddToCart, getString(R.string.addedCart), Snackbar.LENGTH_SHORT).show()
                        } else { // UPDATE; Se existir o produto no carrinho.
                            Thread {
                                val db = Room.databaseBuilder(this, AppDataBase::class.java, "db")
                                    .build()
                                val product = db.Produto_carrinhoDAO().listProduct(book.toInt())
                                runOnUiThread {
                                    val qnt = binding.editQuantity.text.toString()
                                    product.qtde += qnt.toInt()
                                    updateProductQuantity(product)
                                    Snackbar.make(binding.btnAddToCart,
                                        "${getString(R.string.alreadyInCart)} $qnt",
                                        Snackbar.LENGTH_SHORT).show()

                                }
                            }.start()
                        }
                    }else{
                        Snackbar.make(binding.btnAddToCart,
                            getString(R.string.specifyQuantity),
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }.start()
    }

    fun getBookInfo(bookId: String,i: Int){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://projetofinal-android-default-rtdb.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ProductService::class.java)

        val call = service.getBookInfo(bookId)

        val callback = object : Callback<Produto> {
            override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                progressInvisible()
                if (response.isSuccessful) {
                    val product = response.body()
                    if(i == 0) {
                        updateUI(product)
                    }else{
                        updateCart(product,bookId)
                    }
                }
                else {
                    Snackbar.make(
                        binding.txtDescriptionDetail,
                        "N??o foi poss??vel conectar-se ao servidor.",
                        Snackbar.LENGTH_LONG
                    ).show()

                    Log.e("ERRO-Retrofit", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Produto>, t: Throwable) {
                progressInvisible()
                Snackbar.make(
                    binding.txtDescriptionDetail,
                    "N??o foi poss??vel atualizar os produtos.",
                    Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha na conex??o", t)
            }
        }
        call.enqueue(callback)
        progressVisible()

    }

    fun progressVisible(){
        binding.progressDetails.visibility = View.VISIBLE
        binding.imgProductDetail.visibility = View.INVISIBLE
        binding.txtTitleProductDetail.visibility = View.INVISIBLE
        binding.txtPriceDetail.visibility = View.INVISIBLE
        binding.cardDescription.visibility = View.INVISIBLE
        binding.editQuantity.visibility = View.INVISIBLE
        binding.btnAddToCart.visibility = View.INVISIBLE
        binding.outlinedTextField.visibility = View.INVISIBLE
    }

    fun progressInvisible(){
        binding.progressDetails.visibility = View.INVISIBLE
        binding.imgProductDetail.visibility = View.VISIBLE
        binding.txtTitleProductDetail.visibility = View.VISIBLE
        binding.txtPriceDetail.visibility = View.VISIBLE
        binding.cardDescription.visibility = View.VISIBLE
        binding.editQuantity.visibility = View.VISIBLE
        binding.btnAddToCart.visibility = View.VISIBLE
        binding.outlinedTextField.visibility = View.VISIBLE
    }

    fun updateUI(info: Produto?){
        if (info != null) {
            binding.txtTitleProductDetail.text = info.Titulo
            val oldPrice = String.format("%.2f", info.preco)
            val price = String.format("%.2f", info.preco - info.desconto)
            binding.txtPriceDetail.text = "${getString(R.string.saleOldPrice)}: \$ ${oldPrice} \n${getString(R.string.saleNewPrice)}: \$ ${price}"
            Picasso.get()
                .load(info.Capa)
                .resize(400, 550)
                .centerCrop()
                .into(binding.imgProductDetail)

            binding.txtDescriptionDetail.text = "${getString(R.string.author)}: ${info.Autor}\n" +
                    "${getString(R.string.categoryDrawer)}: ${info.Categoria}\n" +
                    "${getString(R.string.numPag)}: ${info.numPag}\n\n${info.Descricao}"
        }
    }
}