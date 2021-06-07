package com.example.projetofinal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinal.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)

        binding.btnCategoryAll.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "All")
            startActivity(intent)
        }
        binding.btnCategoryFantasy.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Fantasia")
            startActivity(intent)
        }
        binding.btnCategoryFiction.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Ficção")
            startActivity(intent)
        }
        binding.btnCategoryOther.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Outros")
            startActivity(intent)
        }
        binding.btnCategorySelfhelp.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Autoajuda")
            startActivity(intent)
        }
        binding.btnCategoryThriller.setOnClickListener {
            val intent = Intent(this.activity, AllProductsActivity::class.java)
            intent.putExtra("requestType", "Suspense")
            startActivity(intent)
        }

        return binding.root
    }


}