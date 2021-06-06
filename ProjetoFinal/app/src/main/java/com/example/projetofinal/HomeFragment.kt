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


        return binding.root
    }

    fun setIntent(card: CardItemBinding){
        card.cardProductTitle.setOnClickListener {
            val productInfo = Intent(this.activity, ProductActivity::class.java)
            startActivity(productInfo)
        }
        card.cardProductImage.setOnClickListener {
            val productInfo = Intent(this.activity, ProductActivity::class.java)
            startActivity(productInfo)
        }
        card.cardProductPrice.setOnClickListener {
            val productInfo = Intent(this.activity, ProductActivity::class.java)
            startActivity(productInfo)
        }
        binding.offersContainer.addView(card.root)

    }



}