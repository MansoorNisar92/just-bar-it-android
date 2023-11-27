package com.android.app.justbarit.presentation.common.ext

import androidx.appcompat.widget.AppCompatImageView
import com.android.app.justbarit.R
import com.android.app.justbarit.presentation.common.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun AppCompatImageView.loadImageFromAssets(
    resourceId: Int?,
    placeHolder: Int = R.drawable.ic_default_image_placeholder
) {
    Glide.with(this).load(resourceId).apply(RequestOptions()).placeholder(placeHolder).into(this)
}


fun AppCompatImageView.loadImageWithImageId(
    imageId: String?,
    placeHolder: Int = R.drawable.ic_default_image_placeholder
) {
    val imageUrl = "${Constants.imageBaseUrl}${imageId}"
    Glide.with(this)
        .load(imageUrl).apply(RequestOptions()).placeholder(placeHolder).into(this)
}