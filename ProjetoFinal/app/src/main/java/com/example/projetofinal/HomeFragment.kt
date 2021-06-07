package com.example.projetofinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)

        binding.btnHomeCategoryAll.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "All")
            startActivity(intent)
        }
        binding.btnHomeCategoryFiction.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Ficção")
            startActivity(intent)
        }
        binding.btnHomeCategoryThriller.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Suspense")
            startActivity(intent)
        }


        return binding.root
    }

    fun setIntent(card: CardItemBinding){
        val productInfo = Intent(this.activity, ProductActivity::class.java)
        productInfo.putExtra("idBook", card.cardBookId.text)
        card.cardProductTitle.setOnClickListener {
            startActivity(productInfo)
        }
        card.cardProductImage.setOnClickListener {
            startActivity(productInfo)
        }
        card.cardProductPrice.setOnClickListener {
            startActivity(productInfo)
        }
        binding.offersContainer.addView(card.root)

    }



}