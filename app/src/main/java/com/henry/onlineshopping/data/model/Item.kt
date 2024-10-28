package com.henry.onlineshopping.data.model

data class Item(
    val title: String = "",
    val description: String = "",
    val pictureUrl: List<String> = listOf(),
    val size: List<String> = listOf(),
    val price: Double,
    val rating : Double,
    val productFoundInCart: Int,
    val categoryId: Int,
    val sellerName: String,
    val sellerTel: Int,
    val sellerAvatar: String
)