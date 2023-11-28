@file:Suppress("unused")

package com.android.app.justbarit.presentation.common.customviews.bottomnavigation.forms

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Dp
import com.android.app.justbarit.presentation.common.ext.contextColor
import com.android.app.justbarit.presentation.common.ext.dp2Px
import com.android.app.justbarit.presentation.common.ext.resourceDrawable

@DslMarker
internal annotation class IconFormDsl

/** creates an instance of [IconForm] from [IconForm.Builder] using kotlin dsl. */
@IconFormDsl
@JvmSynthetic
inline fun iconForm(context: Context, block: IconForm.Builder.() -> Unit): IconForm =
  IconForm.Builder(context).apply(block).build()

/**
 * IconForm is an attribute class that has icon attributes
 * for customizing menu item icons easily.
 */
data class IconForm(

  var icon: Drawable? = null,
  @Px var iconSize: Int = 28,
  @ColorInt var iconColor: Int = Color.WHITE,
  @ColorInt var iconActiveColor: Int = Color.WHITE

) {

  /** Builder class for [IconForm]. */
  @IconFormDsl
  data class Builder(private val context: Context) {

    @JvmField
    var icon: Drawable? = null

    @Px
    @JvmField
    var iconSize: Int = context.dp2Px(28)

    @ColorInt
    @JvmField
    var iconColor: Int = Color.WHITE

    @ColorInt
    @JvmField
    var iconActiveColor: Int = Color.WHITE

    /** sets the [Drawable] of the icon. */
    fun setIcon(value: Drawable?) = apply { this.icon = value }

    /** sets the [Drawable] of the icon using resource. */
    fun setIcon(@DrawableRes value: Int) = apply {
      this.icon = context.resourceDrawable(value)
    }

    /** sets the size of the icon. */
    fun setIconSize(@Dp value: Int) = apply { this.iconSize = context.dp2Px(value) }

    /** sets the color of the icon. */
    fun setIconColor(@ColorInt value: Int) = apply { this.iconColor = value }

    /** sets the color of the icon using resource */
    fun setIconColorRes(@ColorRes value: Int) = apply {
      this.iconColor = context.contextColor(value)
    }

    /** sets the active color of the icon. */
    fun setIconActiveColor(@ColorInt value: Int) = apply { this.iconActiveColor = value }

    /** sets the active color of the icon using resource */
    fun setIconActiveColorRes(@ColorRes value: Int) = apply {
      this.iconActiveColor = context.contextColor(value)
    }

    fun build() = IconForm(icon, iconSize, iconColor, iconActiveColor)
  }
}
