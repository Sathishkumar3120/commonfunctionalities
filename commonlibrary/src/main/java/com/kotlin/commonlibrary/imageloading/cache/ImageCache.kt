package com.kotlin.commonlibrary.imageloading.cache

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache


/**
 * Class do - ImageCache
 */
class ImageCache(maxSize: Int) : LruCache<String, Bitmap>(maxSize) {

    override fun sizeOf(key: String?, value: Bitmap?): Int {
        return value!!.rowBytes * value.height
    }

    companion object {

        private var instance: ImageCache? = null

        operator fun get(context: Context?, cacheAllowed: Float): ImageCache {
            if (instance == null) {
                val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val memClassBytes = (am.memoryClass * 1024 * 1024).toFloat()
                val cacheSize = memClassBytes * cacheAllowed
                instance = ImageCache(Math.round(cacheSize))
            }
            return instance as ImageCache
        }
    }
}
