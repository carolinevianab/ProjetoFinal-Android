package com.example.projetofinal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Produto_carrinho(
        @PrimaryKey
        var id : Int,
        var titulo: String,
        var preco: Double,
        var qtde: Int
)