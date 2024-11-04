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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart")

class TinyDB(
    val context: Context
) {
//    private lateinit var imageDir: String
//    private var lastImagePath: String = ""
//
//    fun isExternalStorageWritable() =
//        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
//
//    fun isExternalStorageReadable() =
//        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY
//
//    fun getImage(path: String): Bitmap? = BitmapFactory.decodeFile(path)
//
//    fun getSavedImagePath() = lastImagePath
//
//    fun saveImage(folder: String?, bitmapName: String?, bitmap: Bitmap?): String? =
//        if (folder == null || bitmapName == null || bitmap == null) {
//            null
//        } else {
//            imageDir = folder
//            val fullPath: String = setupFullPath(bitmapName)
//            if (fullPath.isBlank()) {
//                lastImagePath = fullPath
//                saveBitmap(fullPath, bitmap)
//            }
//            fullPath
//        }


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

    private suspend fun putListString(key: String?, objString: MutableList<String>) {
        val arrayOfString = objString.toTypedArray()
        context.dataStore.edit { settings ->
            if (key != null) {
                settings[stringPreferencesKey(key)] = TextUtils.join("‚‗‚", arrayOfString)
            }
        }
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

    private suspend fun getListString(key: String): ArrayList<String> {
        val key1 = stringPreferencesKey(key)
        val value = context.dataStore.data.map { pref ->
            pref[key1] ?: ""
        }.first()

        return ArrayList(value.split("‚‗‚"))
    }

//    /**
//     * Saves [bitmap] into the specified [path].
//     * @param path Full path of the image (e.g. "images/MeAtLunch.png").
//     * @param bitmap The image to be saved as [Bitmap].
//     * @return `true` if image can be saved, `false` otherwise.
//     */
//    fun saveImageWithFullPath(path: String?, bitmap: Bitmap?) =
//        !(path == null || bitmap == null) && saveBitmap(path, bitmap)
//
//    private fun setupFullPath(imageName: String): String {
//        val folder = File(Environment.getExternalStorageDirectory(), imageDir)
//
//        if (isExternalStorageReadable() && isExternalStorageWritable() && !folder.exists()) {
//            if (!folder.mkdirs()) return ""
//        }
//
//        return "${folder.path}/$imageName"
//    }
//
//    private fun saveBitmap(path: String?, bitmap: Bitmap?): Boolean {
//        if (path == null || bitmap == null) return false
//
//        val imageFile = File(path)
//        if (imageFile.exists() && !imageFile.delete()) return false
//
//        return runCatching {
//            imageFile.createNewFile() && FileOutputStream(imageFile).use { stream ->
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            }
//        }.getOrElse {
//            Log.e("TinyDB", it.message, it.cause)
//            false
//        }
//    }
}