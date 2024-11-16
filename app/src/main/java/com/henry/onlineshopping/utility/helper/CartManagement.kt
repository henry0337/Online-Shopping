package com.henry.onlineshopping.utility.helper

import android.content.Context
import android.widget.Toast
import com.henry.onlineshopping.data.model.Item

class CartManagement(
    val context: Context
) {
    private val db = CartLogicalHandler(context)

    suspend fun insertItem(item: Item) {
        val list = getListCart()
        var isExisted = false
        var index = 1

        for (i in 0 until list.size) {
            if (list[i].title == item.title) {
                isExisted = true
                index = i
                break
            }
        }

        if (isExisted) {
            list[index].cartProductCount = item.cartProductCount
        } else {
            list.add(item)
        }

        db.putListObject("CartList", list)
        Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show()
    }

    suspend fun decreaseOrRemoveItem(list: MutableList<Item>, position: Int, listener: ChangeNumberOfItemsListener) {
        if (list[position].cartProductCount == 1) {
            list.removeAt(position)
        } else {
            list[position].cartProductCount -= 1
        }

        db.putListObject("CartList", list)
        listener.onChange()
    }

    suspend fun increaseItem(list: MutableList<Item>, position: Int, listener: ChangeNumberOfItemsListener) {
        list[position].cartProductCount += 1
        db.putListObject("CartList", list)
        listener.onChange()
    }

    private suspend fun getListCart() = db.getListObject("CartList")
}