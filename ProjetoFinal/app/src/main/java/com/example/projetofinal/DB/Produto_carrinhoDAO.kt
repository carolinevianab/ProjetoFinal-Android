package com.example.projetofinal.DB

import androidx.room.*
import com.example.projetofinal.model.Produto_carrinho

@Dao
interface Produto_carrinhoDAO {
    @Insert
    fun insert(product: Produto_carrinho)

    @Update
    fun update(product: Produto_carrinho)

    @Query(value = "select * from Produto_carrinho")
    fun listAll(): List<Produto_carrinho>

    @Query(value = "select * from Produto_carrinho where id = :id")
    fun listProduct(id: Int): Produto_carrinho

    @Query(value = "delete from Produto_carrinho")
    fun cleanCart()
}