package com.kotlin.commonlibrary.imageloading.extension

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.kotlin.commonlibrary.imageloading.ImageLoader

/**
 * Class do - ImageView.source
 */

var ImageView.source: Any
    get() = ""
    set(value) {
        if (value is String) {
            ImageLoader.with(this.context).source(value).loadImage(this)
        }
        else if (value is Int)
            ImageLoader.with(this.context).source(value).loadImage(this)
    }

var ImageView.placeholder: Drawable?
    get() = null
    set(source) {
        this.setImageDrawable(source)
    }
