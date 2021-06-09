package com.example.projetofinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import com.example.projetofinal.DB.AppDataBase
import com.example.projetofinal.databinding.CardCartItemBinding
import com.example.projetofinal.databinding.FragmentCartBinding
import com.example.projetofinal.model.Produto_carrinho
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater)


        if (getUser() == null) {
            val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                if (result.resultCode == Activity.RESULT_OK){
                    val data: Intent? = result.data
                    updateActivity()

                }

            }

            val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
            val intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()

            startForResult.launch(intent)
        }
        else {
            updateActivity()
        }

        return binding.root
    }

    fun updateActivity(){
        binding.btnEndPurchase.setOnClickListener {
            val i = Intent(activity, FinishPurchaseActivity::class.java)
            startActivity(i)
        }
        refreshProducts()
    }

    override fun onResume() {
        super.onResume()

        if (getUser() != null) {
            refreshProducts()
        }
    }

    fun refreshProducts(){
        Thread{
            val db = Room.databaseBuilder(this.requireContext(), AppDataBase::class.java, "db" ).build()
            val products = db.Produto_carrinhoDAO().listAll()
            activity?.runOnUiThread {
                updateUi(products)
            }
        }.start()
    }

    fun updateUi(products: List<Produto_carrinho>){
        val cardBinding = binding.cartContainer.removeAllViews()

        if(products.isNotEmpty()){
            binding.txtEmptyCart.visibility = View.INVISIBLE
            binding.btnEndPurchase.isEnabled = true
        }else{
            binding.txtEmptyCart.visibility = View.VISIBLE
            binding.btnEndPurchase.isEnabled = false
        }

        var total = 0.0

        products.forEach{
            val cardBinding = CardCartItemBinding.inflate(layoutInflater)

            cardBinding.cardCartProductTitle.text = it.titulo
            cardBinding.cardCartProductPrice.text = it.preco.toString()
            cardBinding.cardCartQuantity.text = "${getString(R.string.quantity)}: ${it.qtde.toString()}"
            Picasso.get()
                .load(it.capa)
                .resize(300, 450)
                .centerCrop()
                .into(cardBinding.cardCartProductImage)

            total += (it.preco*it.qtde)

            binding.cartContainer.addView(cardBinding.root)
        }
        binding.txtSubtotal.text = "$" + String.format("%.2f", total)
    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}