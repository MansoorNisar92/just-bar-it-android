package com.android.app.justbarit.presentation.common.ext

import android.content.res.ColorStateList
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.forms.IconForm


/** applies icon form attributes to a ImageView instance. */
internal fun AppCompatImageView.applyIconForm(iconForm: IconForm) {
  iconForm.icon?.let {
    setImageDrawable(it)
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(iconForm.iconColor))
    layoutParams = FrameLayout.LayoutParams(iconForm.iconSize, iconForm.iconSize, Gravity.CENTER)
  }
}
