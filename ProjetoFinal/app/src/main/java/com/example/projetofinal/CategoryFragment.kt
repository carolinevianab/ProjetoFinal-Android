package com.example.projetofinal

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

        return binding.root
    }


}