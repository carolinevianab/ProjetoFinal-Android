package com.example.projetofinal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinal.databinding.FragmentAboutBinding
import com.squareup.picasso.Picasso

class AboutFragment : Fragment() {
    lateinit var binding: FragmentAboutBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater)

        Picasso.get().load("https://www.sp.senac.br/o/senac-theme/images/logo_senac.png").into(binding.imgSenac)

        binding.imgSenac.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sp.senac.br"))
            startActivity(i)
        }

        binding.imgMailCarol.setOnClickListener {
            sendEmail()
        }

        binding.imgMailRichard.setOnClickListener {
            sendEmail()
        }

        return binding.root
    }

    fun sendEmail(){
    }
}