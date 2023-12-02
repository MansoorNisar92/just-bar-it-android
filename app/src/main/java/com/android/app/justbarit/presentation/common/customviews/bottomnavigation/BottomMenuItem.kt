@file:Suppress("unused")

package com.android.app.justbarit.presentation.common.customviews.bottomnavigation

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Dp
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations.Sp
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.forms.IconForm
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.forms.TitleForm
import com.android.app.justbarit.presentation.common.ext.contextColor
import com.android.app.justbarit.presentation.common.ext.dp2Px
import com.android.app.justbarit.presentation.common.ext.resourceDrawable

/**
 * [BottomMenuItem] is a collection of [BottomMenuItemView] attributes.
 * Composed to [TitleForm], [IconForm] with fully customizable.
 * Each form can be reused for setting up multiple menu items.
 */
open class BottomMenuItem(val context: Context) {

  @JvmField
  var titleForm = TitleForm()

  @JvmField
  var iconForm = IconForm()


  /** sets a customized [TitleForm]. */
  fun setTitleForm(value: TitleForm) = apply { this.titleForm = value.copy() }

  /** sets a customized [IconForm]. */
  fun setIconForm(value: IconForm) = apply { this.iconForm = value.copy() }

  /** sets the content of the title. */
  fun setTitle(value: CharSequence) = apply { this.titleForm.title = value }

  /** sets the content of the title using string resource. */
  fun setTitle(@StringRes value: Int) = apply { this.titleForm.title = context.getString(value) }

  /** sets the color of the title. */
  fun setTitleColor(@ColorInt value: Int) = apply { this.titleForm.titleColor = value }

  /** sets the color of the title using resource. */
  fun setTitleColorRes(@ColorRes value: Int) = apply { this.titleForm.titleColor = context.contextColor(value) }

  /** sets the color of the title. */
  fun setTitleActiveColor(@ColorInt value: Int) = apply { this.titleForm.titleActiveColor = value }

  /** sets the color of the title using resource. */
  fun setTitleActiveColorRes(@ColorRes value: Int) = apply { this.titleForm.titleActiveColor = context.contextColor(value) }

  /** sets the size of the title. */
  fun setTitleSize(@Sp value: Float) = apply { this.titleForm.titleSize = value }

  /** sets the [Typeface] of the title. */
  fun setTitleTypeface(value: Int) = apply { this.titleForm.titleStyle = value }

  /** sets the [Typeface] of the title. */
  fun setTitleTypeface(value: Typeface?) = apply { this.titleForm.titleTypeface = value }

  /** sets the padding of the title. */
  fun setTitlePadding(@Dp value: Int) = apply { this.titleForm.titlePadding = context.dp2Px(value) }

  /** sets gravity of the title. */
  fun setTitleGravity(value: Int) = apply { this.titleForm.titleGravity = value }

  /** sets the [Drawable] of the icon. */
  fun setIcon(value: Drawable?) = apply { this.iconForm.icon = value }

  /** sets the [Drawable] of the icon using resource. */
  fun setIcon(@DrawableRes value: Int) = apply {
    this.iconForm.icon = context.resourceDrawable(value)
  }

  /** sets the size of the icon. */
  fun setIconSize(@Dp value: Int) = apply { this.iconForm.iconSize = context.dp2Px(value) }

  /** sets the color of the icon. */
  fun setIconColor(@ColorInt value: Int) = apply { this.iconForm.iconColor = value }

  /** sets the color of the icon using resource */
  fun setIconColorRes(@ColorRes value: Int) = apply {
    this.iconForm.iconColor = context.contextColor(value)
  }

  /** sets the color of the icon. */
  fun setIconActiveColor(@ColorInt value: Int) = apply { this.iconForm.iconActiveColor = value }

  /** sets the color of the icon using resource */
  fun setIconActiveColorRes(@ColorRes value: Int) = apply {
    this.iconForm.iconActiveColor = context.contextColor(value)
  }

  /** returns a [BottomMenuItem]. */
  fun build() = this
}
