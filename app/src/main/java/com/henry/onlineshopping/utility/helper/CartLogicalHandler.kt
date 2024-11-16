package com.henry.onlineshopping.utility.helper

import android.content.Context
import android.text.TextUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.henry.onlineshopping.data.model.Item
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

class CartLogicalHandler(
    val context: Context
) {
    suspend fun putListObject(key: String?, list: MutableList<Item>) {
        if (key == null) throw Exception()

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Item::class.java)
        val objString = mutableListOf<String>()

        for (item: Item in list) {
            objString.add(adapter.toJson(item))
        }

        putListString(key, objString)
    }

    suspend fun getListObject(key: String): MutableList<Item> {
        val objString = getListString(key)
        val items = mutableListOf<Item>()

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Item::class.java)

        for (child: String in objString) {
            val item = adapter.fromJson(child)
            if (item != null) {
                items.add(item)
            }
        }

        return items
    }

    private suspend fun putListString(key: String?, objString: MutableList<String>) {
        val arrayOfString = objString.toTypedArray()
        context.dataStore.edit { settings ->
            if (key != null) {
                settings[stringPreferencesKey(key)] = TextUtils.join("‚‗‚", arrayOfString)
            }
        }
    }

    private suspend fun getListString(key: String): ArrayList<String> {
        val key1 = stringPreferencesKey(key)
        val value = context.dataStore.data.map { pref ->
            pref[key1] ?: ""
        }.first()

        return ArrayList(value.split("‚‗‚"))
    }
}