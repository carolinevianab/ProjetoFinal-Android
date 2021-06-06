package com.example.projetofinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinal.databinding.FragmentCartBinding


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


}