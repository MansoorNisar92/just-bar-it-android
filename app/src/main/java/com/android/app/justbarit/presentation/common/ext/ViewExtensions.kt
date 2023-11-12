package com.android.app.justbarit.presentation.common.ext

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.android.app.justbarit.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun View.clickToAction(action: () -> Unit = {}) {
    setOnClickListener {
        hideKeyboard()
        action()
    }
}

fun View.hideKeyboard() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        windowToken,
        0
    )
}

fun ViewGroup.inflate(@LayoutRes resourceId: Int): View = View.inflate(context, resourceId, this)

fun Int.bitmapFromVector(context: Context): BitmapDescriptor {
    // below line is use to generate a drawable.
    val vectorDrawable = ContextCompat.getDrawable(context, this)

    // below line is use to set bounds to our vector
    // drawable.
    vectorDrawable!!.setBounds(
        0, 0, vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )

    // below line is use to create a bitmap for our
    // drawable which we have added.
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // below line is use to add bitmap in our canvas.
    val canvas = Canvas(bitmap)

    // below line is use to draw our
    // vector drawable in canvas.
    vectorDrawable.draw(canvas)
    val height = 60
    val width = 60
    val smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false)

    // after generating our bitmap we are returning our
    // bitmap.
    return BitmapDescriptorFactory.fromBitmap(smallMarker)
}

fun View.scaleUpAnimation() {
    val animation: Animation = AnimationUtils.loadAnimation(this.context, R.anim.scale_up)
    startAnimation(animation)
}