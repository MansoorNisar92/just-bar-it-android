package com.android.app.justbarit.presentation.common.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Dp

/** dp size to px size. */
internal fun Context.dp2Px(@Dp dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).toInt()
}

/** dp size to px size. */
internal fun Context.dp2Px(@Dp dp: Float): Float {
    val scale = resources.displayMetrics.density
    return dp * scale
}

/** gets an accent color. */
@ColorInt
internal fun Context.accentColor(): Int {
    val colorAttr: Int = resources.getIdentifier("colorAccent", "attr", packageName)
    val outValue = TypedValue()
    theme.resolveAttribute(colorAttr, outValue, true)
    return outValue.data
}

/** gets a color from the resource. */
@ColorInt
internal fun Context.contextColor(@ColorRes resource: Int): Int {
    return ContextCompat.getColor(this, resource)
}

/** gets a drawable from the resource. */
internal fun Context.resourceDrawable(@DrawableRes resource: Int): Drawable? {
    return AppCompatResources.getDrawable(this, resource)
}
