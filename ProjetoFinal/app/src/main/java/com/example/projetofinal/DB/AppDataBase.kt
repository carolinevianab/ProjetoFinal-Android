package com.example.projetofinal.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projetofinal.model.Produto_carrinho

@Database(entities = arrayOf(Produto_carrinho::class), version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun Produto_carrinhoDAO(): Produto_carrinhoDAO
}