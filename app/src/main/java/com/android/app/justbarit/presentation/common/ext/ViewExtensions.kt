package com.android.app.justbarit.presentation.common.ext

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.android.app.justbarit.R
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.BottomMenuItemViewConfig
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Dp
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.getInterpolator
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

fun View.propagationAnimation() {
    val animation: Animation = AnimationUtils.loadAnimation(this.context, R.anim.propgration_animation)
    startAnimation(animation)
}

fun View.fallDownAnimation() {
    val animation: Animation = AnimationUtils.loadAnimation(this.context, R.anim.item_animation_fall_down)
    startAnimation(animation)
}

val Boolean?.default
    get() = this ?: false


/** dp size to px size. */
internal fun View.dp2Px(@Dp dp: Int) = context.dp2Px(dp)

/** dp size to px size. */
internal fun View.dp2Px(@Dp dp: Float) = context.dp2Px(dp)

/** makes visible or gone a view align the value parameter. */
internal fun View.visible(value: Boolean) {
    if (value) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

/** translate x axis of a view. */
internal fun View.translateX(
    from: Float,
    to: Float,
    config: BottomMenuItemViewConfig
) {
    ValueAnimator.ofFloat(from, to).apply {
        this.duration = config.duration
        this.interpolator = config.animation.getInterpolator()
        addUpdateListener {
            x = it.animatedValue as Float
        }
    }.start()
}

/** translate y axis of a view. */
internal fun View.translateY(
    from: Float,
    to: Float,
    config: BottomMenuItemViewConfig,
    onStart: (View) -> Unit = {},
    onEnd: (View) -> Unit = {}
) {
    startAnimation(
        TranslateAnimation(0f, 0f, from, to).apply {
            this.duration = config.duration
            this.interpolator = config.animation.getInterpolator()
            this.setAnimationListener(
                object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) = onStart(this@translateY)
                    override fun onAnimationEnd(p0: Animation?) = onEnd(this@translateY)
                    override fun onAnimationRepeat(p0: Animation?) = Unit
                }
            )
        }
    )
}