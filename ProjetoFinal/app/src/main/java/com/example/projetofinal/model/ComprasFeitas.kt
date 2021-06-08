package com.example.projetofinal.model

data class BooksBought (
    var BookId: Int,
    var qtd: Int
)


data class Purchase (
    var purId: String
)

data class AllPurchases(
    var purId: String,
    var myBooks: List<BooksBought>
)