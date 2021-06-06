package com.example.projetofinal.model

data class Produto (
        var Autor: String,
        var Categoria: String,
        var DataLancamento: String,
        var Descricao: String,
        var Editora: String,
        var Titulo: String,
        var desconto: Double,
        var id: Int,
        var numPag: Int,
        var preco: Double
)