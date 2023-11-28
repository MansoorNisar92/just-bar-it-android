package com.android.app.justbarit.presentation.common.customviews.bottomnavigation

import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator

/**
 * BottomMenuAnimation is an enumeration class for
 * animating selected or unselected BottomMenuItemView with an interpolator.
 */
@Suppress("unused")
enum class BottomMenuAnimation(val value: Int) {
  NORMAL(0),
  ACCELERATE(1),
  BOUNCE(2),
  OVERSHOOT(3),
}

/** returns an interpolator from the [BottomMenuAnimation]. */
fun BottomMenuAnimation.getInterpolator(): Interpolator {
  return when (this) {
    BottomMenuAnimation.NORMAL -> LinearInterpolator()
    BottomMenuAnimation.ACCELERATE -> AccelerateInterpolator()
    BottomMenuAnimation.BOUNCE -> BounceInterpolator()
    BottomMenuAnimation.OVERSHOOT -> OvershootInterpolator()
  }
}
