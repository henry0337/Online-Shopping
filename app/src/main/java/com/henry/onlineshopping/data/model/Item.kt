package com.henry.onlineshopping.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val title: String = "",
    val description: String = "",
    val pictureUrl: List<String> = listOf(),
    val size: List<String> = listOf(),
    val price: Double,
    val rating : Double,
    var cartProductCount: Int,
    val categoryId: Int,
    val sellerName: String,
    val sellerTel: Int,
    val sellerAvatar: String
) : Parcelable