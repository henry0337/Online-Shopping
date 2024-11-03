package com.henry.onlineshopping.utility.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

object TinyDB {
    private lateinit var imageDir: String
    private var lastImagePath: String = ""

    fun isExternalStorageWritable() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun isExternalStorageReadable() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY

    fun getImage(path: String): Bitmap? = BitmapFactory.decodeFile(path)

    fun getSavedImagePath() = lastImagePath

    fun saveImage(folder: String?, bitmapName: String?, bitmap: Bitmap?): String? =
        if (folder == null || bitmapName == null || bitmap == null) {
            null
        } else {
            imageDir = folder
            val fullPath: String = setupFullPath(bitmapName)
            if (fullPath.isBlank()) {
                lastImagePath = fullPath
                saveBitmap(fullPath, bitmap)
            }
            fullPath
        }

    /**
     * Saves [bitmap] into the specified [path].
     * @param path Full path of the image (e.g. "images/MeAtLunch.png").
     * @param bitmap The image to be saved as [Bitmap].
     * @return `true` if image can be saved, `false` otherwise.
     */
    fun saveImageWithFullPath(path: String?, bitmap: Bitmap?) =
        !(path == null || bitmap == null) && saveBitmap(path, bitmap)

    private fun setupFullPath(imageName: String): String {
        val folder = File(Environment.getExternalStorageDirectory(), imageDir)

        if (isExternalStorageReadable() && isExternalStorageWritable() && !folder.exists()) {
            if (!folder.mkdirs()) return ""
        }

        return "${folder.path}/$imageName"
    }

    private fun saveBitmap(path: String?, bitmap: Bitmap?): Boolean {
        if (path == null || bitmap == null) return false

        val imageFile = File(path)
        if (imageFile.exists() && !imageFile.delete()) return false

        return runCatching {
            imageFile.createNewFile() && FileOutputStream(imageFile).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            }
        }.getOrElse {
            Log.e("TinyDB", it.message, it.cause)
            false
        }
    }
}