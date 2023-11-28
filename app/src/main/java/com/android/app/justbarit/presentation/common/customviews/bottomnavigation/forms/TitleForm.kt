@file:Suppress("unused")

package com.android.app.justbarit.presentation.common.customviews.bottomnavigation.forms

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Dp
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Sp
import com.android.app.justbarit.presentation.common.ext.contextColor
import com.android.app.justbarit.presentation.common.ext.dp2Px

@DslMarker
internal annotation class TitleFormDsl

/** creates an instance of [TitleForm] from [TitleForm.Builder] using kotlin dsl. */
@TitleFormDsl
@JvmSynthetic
inline fun titleForm(context: Context, block: TitleForm.Builder.() -> Unit): TitleForm =
  TitleForm.Builder(context).apply(block).build()

/**
 * [TitleForm] is an attribute class that has some attributes about text
 * for customizing menu item title easily.
 */
data class TitleForm(

  var title: CharSequence = "",
  var titleStyle: Int = Typeface.NORMAL,
  var titleTypeface: Typeface? = null,
  var titleGravity: Int = Gravity.CENTER,
  @Px var titlePadding: Int = 6,
  @Sp var titleSize: Float = 14f,
  @ColorInt var titleColor: Int = Color.WHITE,
  @ColorInt var titleActiveColor: Int = Color.WHITE

) {

  /** Builder class for [TitleForm]. */
  @TitleFormDsl
  data class Builder(private val context: Context) {

    @JvmField
    var title: CharSequence = ""

    @ColorInt
    @JvmField
    var titleColor: Int = Color.WHITE

    @ColorInt
    @JvmField
    var titleActiveColor: Int = Color.WHITE

    @Sp
    @JvmField
    var titleSize: Float = 14f

    @JvmField
    var titleStyle = Typeface.NORMAL

    @JvmField
    var titleTypeface: Typeface? = null

    @Px
    @JvmField
    var titlePadding: Int = context.dp2Px(6)

    @JvmField
    var titleGravity: Int = Gravity.CENTER

    /** sets the content of the title. */
    fun setTitle(value: CharSequence) = apply { this.title = value }

    /** sets the content of the title using string resource. */
    fun setTitle(@StringRes value: Int) = apply { this.title = context.getString(value) }

    /** sets the color of the title. */
    fun setTitleColor(@ColorInt value: Int) = apply { this.titleColor = value }

    /** sets the color of the title using resource. */
    fun setTitleColorRes(@ColorRes value: Int) = apply { this.titleColor = context.contextColor(value) }

    /** sets the active color of the title. */
    fun setTitleActiveColor(@ColorInt value: Int) = apply { this.titleActiveColor = value }

    /** sets the active color of the title using resource. */
    fun setTitleActiveColorRes(@ColorRes value: Int) = apply { this.titleActiveColor = context.contextColor(value) }

    /** sets the size of the title. */
    fun setTitleSize(@Sp value: Float) = apply { this.titleSize = value }

    /** sets the [Typeface] of the title. */
    fun setTitleStyle(value: Int) = apply { this.titleStyle = value }

    /** sets the [Typeface] of the title. */
    fun setTitleTypeface(value: Typeface?) = apply { this.titleTypeface = value }

    /** sets the padding of the title. */
    fun setTitlePadding(@Dp value: Int) = apply { this.titlePadding = context.dp2Px(value) }

    /** sets gravity of the title. */
    fun setTitleGravity(value: Int) = apply { this.titleGravity = value }

    fun build() = TitleForm(
      title,
      titleStyle,
      titleTypeface,
      titleGravity,
      titlePadding,
      titleSize,
      titleColor,
      titleActiveColor
    )
  }
}
