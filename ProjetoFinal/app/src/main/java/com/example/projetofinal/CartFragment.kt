package com.example.projetofinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.projetofinal.DB.AppDataBase
import com.example.projetofinal.databinding.CardCartItemBinding
import com.example.projetofinal.databinding.FragmentCartBinding
import com.example.projetofinal.model.Produto_carrinho
import com.squareup.picasso.Picasso


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater)

        binding.btnEndPurchase.setOnClickListener {
            val i = Intent(activity, FinishPurchaseActivity::class.java)
            startActivity(i)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        refreshProducts()
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
        }else{
            binding.txtEmptyCart.visibility = View.VISIBLE
        }

        var total = 0.0

        products.forEach{
            val cardBinding = CardCartItemBinding.inflate(layoutInflater)

            cardBinding.cardCartProductTitle.text = it.titulo
            cardBinding.cardCartProductPrice.text = it.preco.toString()
            cardBinding.cardCartQuantity.text = it.qtde.toString()
            Picasso.get().load(it.capa).into(cardBinding.cardCartProductImage)

            total += (it.preco*it.qtde)

            binding.cartContainer.addView(cardBinding.root)
        }
        binding.txtSubtotal.text = String.format("%.2f", total)
    }
}