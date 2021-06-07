package com.example.projetofinal.services

import com.example.projetofinal.model.Produto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("/ListaProdutos.json")
    fun listProducts(): Call<List<Produto>>

    @GET("/ListaProdutos/{id}.json")
    fun getBookInfo(@Path("id") id: String): Call<Produto>

    @GET("/ListaProdutos/{id}.json")
    fun getBooksForCategory(@Path("id") id: String): Call<Produto>
}