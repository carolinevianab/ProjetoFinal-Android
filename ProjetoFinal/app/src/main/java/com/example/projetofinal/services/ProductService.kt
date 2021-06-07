package com.example.projetofinal.services

import com.example.projetofinal.model.AllPurchases
import com.example.projetofinal.model.BooksBought
import com.example.projetofinal.model.Produto
import com.example.projetofinal.model.Purchase
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

    @GET("/Compras/Users/{user}/{id}.json")
    fun getFullPurchase(@Path("id") id: String,
                        @Path("user") user: String): Call<AllPurchases>

    @GET("/Compras/Users/{user}.json")
    fun getAllPurchase(@Path("user") user: String): Call<List<Purchase>>

    @GET("/Compras/Users/{caminho}.json")
    fun getAllBooks(@Path("caminho") caminho: String): Call<List<BooksBought>>
}