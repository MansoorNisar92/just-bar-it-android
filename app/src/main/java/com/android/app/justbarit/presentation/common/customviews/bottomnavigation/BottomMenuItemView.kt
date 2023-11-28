package com.android.app.justbarit.presentation.common.customviews.bottomnavigation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.ImageViewCompat
import com.android.app.justbarit.databinding.LayoutBottomMenuItemBinding
import com.android.app.justbarit.presentation.common.ext.applyIconForm
import com.android.app.justbarit.presentation.common.ext.translateY
import com.android.app.justbarit.presentation.common.ext.visible

/**
 * [BottomMenuItemView] is an bottom menu item view with badges and
 * selected/unselected animations. Composed to title, icon, and badge.
 */
class BottomMenuItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs, 0) {

    private val binding: LayoutBottomMenuItemBinding =
        LayoutBottomMenuItemBinding.inflate(LayoutInflater.from(context), this, true)

    private var selectedView: View = binding.icon
    private var unSelectedView: View = binding.icon

    var onMenuItemClickListener:
                (config: BottomMenuItemViewConfig?, view: BottomMenuItemView) -> Unit? = { _, _ -> }

    init {
        binding.root.setOnClickListener { onMenuItemClickListener(config, this) }
    }

    var config: BottomMenuItemViewConfig? = null
        set(value) {
            if (value != null) {
                field = value
                updateBottomMenuItemViewConfig(value)
            }
        }


    private fun updateBottomMenuItemViewConfig(config: BottomMenuItemViewConfig) {
        with(binding) {
            val menuItem = config.bottomMenuItem
            icon.applyIconForm(menuItem.iconForm)
            when (config.bottomBarFlavor) {
                BottomBarFlavor.ICON -> {
                    icon.visible(true)
                    selectedView = icon
                    unSelectedView = icon
                }
            }
        }
    }

    /** sets an activation of the title and icon. */
    fun setIsActive(isActive: Boolean) {
        this.config?.let { config ->
            ImageViewCompat.setImageTintList(
                binding.icon,
                ColorStateList.valueOf(
                    if (isActive) {
                        config.bottomMenuItem.iconForm.iconActiveColor
                    } else {
                        config.bottomMenuItem.iconForm.iconColor
                    }
                )
            )
        }
    }

}
