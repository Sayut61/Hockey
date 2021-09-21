package com.sayut61.hockey.ui.utils

import android.widget.ImageView
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