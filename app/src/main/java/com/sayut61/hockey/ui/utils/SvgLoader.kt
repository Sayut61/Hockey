package com.sayut61.hockey.ui.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import java.util.*

suspend fun ImageView.loadSvg(url: String) {
    if (url.lowercase(Locale.ENGLISH).endsWith("svg")) {
        val imageLoader = ImageLoader.Builder(context)
            .componentRegistry {
                add(SvgDecoder(context))
            }
            .build()
        val request = ImageRequest.Builder(context)
            .data(url)
            .target(this)
            .build()
        imageLoader.execute(request)
    } else {
        load(url)
    }
}


//suspend fun AppCompatImageView.loadSvgOrOthers(myUrl: String?) {
//    myUrl?.let {
//        if (it.lowercase(Locale.ENGLISH).endsWith("svg")) {
//            val imageLoader = ImageLoader.Builder(this.context)
//                .componentRegistry {
//                    add(SvgDecoder(this@loadSvgOrOthers.context))
//                }
//                .build()
//            val request = ImageRequest.Builder(this.context)
//                .data(it)
//                .target(this)
//                .build()
//            imageLoader.execute(request)
//        } else {
//            this.load(myUrl)
//        }
//    }
//}