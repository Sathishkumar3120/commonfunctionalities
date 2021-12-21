package com.kotlin.commonlibrary.imageloading.async

import android.content.Context
import android.widget.ImageView
import com.kotlin.commonlibrary.imageloading.cache.ImageCache
import com.kotlin.commonlibrary.imageloading.manager.ImageFetcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * Class do - LoadBitmapFromDrawableTask
 */
class LoadBitmapFromDrawableTask(private val mContext: Context?, private val imageView: ImageView, private val mCache: ImageCache?, private val mCacheImage: Boolean, private val mWidth: Int, private val mHeight: Int, var res: Int) {
    fun execute() {
        doAsync {
            val bitmap = mContext?.let { ImageFetcher.decodeSampledBitmapFromDrawable(it, res, mWidth, mHeight) }
            uiThread {
                if (mCacheImage) {
                    mCache?.put(res.toString(), bitmap)
                }
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}

