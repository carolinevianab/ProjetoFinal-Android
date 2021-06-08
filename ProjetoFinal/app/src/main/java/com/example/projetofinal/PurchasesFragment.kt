package com.example.projetofinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projetofinal.databinding.CardItemBinding
import com.example.projetofinal.databinding.FragmentPurchasesBinding
import com.example.projetofinal.model.AllPurchases
import com.example.projetofinal.model.Produto
import com.example.projetofinal.model.Purchase
import com.example.projetofinal.services.ProductService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap

class PurchasesFragment : Fragment() {
    lateinit var binding: FragmentPurchasesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPurchasesBinding.inflate(inflater)
        updateList()


        return binding.root
    }

    fun updateList(){
        val user = getUser()
        val databese = FirebaseDatabase.getInstance().reference.child("Compras").child("Users")
                .child(user!!.uid).child("Compra")

        val listener = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                convertData(snapshot)
            }

        }

        databese.addValueEventListener(listener)


    }

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun convertData(copiaDados: DataSnapshot){
        var counter = 1
        copiaDados.children.forEach{
            val id = it.key
            val compra = CardItemBinding.inflate(layoutInflater)
            compra.cardProductImage.setImageResource(R.drawable.shopping_bag)
            compra.cardProductTitle.text = "${getString(R.string.purchase)}${counter}"
            compra.cardProductPrice.text = id.toString()
            compra.cardBookId.visibility = View.INVISIBLE

            val intent = Intent(this.activity, PurchasedActivity::class.java)
            intent.putExtra("compra", id)

            compra.cardProductImage.setOnClickListener {
                startActivity(intent)
            }
            compra.cardProductTitle.setOnClickListener {
                startActivity(intent)
            }
            compra.cardProductPrice.setOnClickListener {
                startActivity(intent)
            }

            binding.purchasesContainer.addView(compra.root)
            counter+=1
        }
    }

}