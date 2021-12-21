package com.kotlin.commonlibrary.imageloading.async

import android.widget.ImageView

import com.kotlin.commonlibrary.imageloading.cache.ImageCache
import com.kotlin.commonlibrary.imageloading.manager.ImageFetcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


import java.net.MalformedURLException
import java.net.URL

/**
 * Class do - LoadBitmapFromURLTask
 */
class LoadBitmapFromURLTask(private val imageView: ImageView, private val mCache: ImageCache?, private val mCacheImage: Boolean, private val mWidth: Int, private val mHeight: Int, private val strUrl: String) {

    fun execute() {
        doAsync {
            var url: URL? = null
            try {
                url = URL(strUrl)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            if (url != null) {
                val bitmap = ImageFetcher.decodeSampledBitmapFromUrl(url, mWidth, mHeight)
                uiThread {
                    if (mCacheImage) {
                        mCache?.put(strUrl, bitmap)
                    }
                    imageView.setImageBitmap(bitmap)

                }
            }
        }
    }
}

