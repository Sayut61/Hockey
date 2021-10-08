package com.sayut61.hockey.ui.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sayut61.hockey.R
import com.sayut61.hockey.ui.utils.svgloader.SvgLoader

fun loadImage(
    imageUrl: String,
    activity: Activity?,
    imageView: ImageView
) {
    if (imageUrl.endsWith("svg")) {
        activity?.let { activity ->
            SvgLoader.pluck()
                .with(activity)
                .setPlaceHolder(R.drawable.ic_progress, R.drawable.ic_error)
                .load(imageUrl, imageView)
        }
    } else
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_progress)
            .error(R.drawable.ic_error)
            .into(imageView)
}