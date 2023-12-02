package com.android.app.justbarit.presentation.dashboard.viewmodel

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.android.app.justbarit.R
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.BottomMenuItem
import com.android.app.justbarit.presentation.common.customviews.bottomnavigation.forms.iconForm
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private var navigationItem = mutableListOf<BottomMenuItem>()
    fun navigationItems(context: Context): MutableList<BottomMenuItem> {
        val list = mutableListOf<BottomMenuItem>()
        context.apply {
            // we can create the form using kotlin dsl.
            val iconHome = iconForm(this) {
                setIcon(R.drawable.home_icon_selected)
                setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
                setIconSize(24) // sets the size of the icon.
            }

            val iconCalendar = iconForm(this) {
                setIcon(R.drawable.calendar_icon_selected)
                setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
                setIconSize(24) // sets the size of the icon.
            }

            val iconSearch = iconForm(this) {
                setIcon(R.drawable.search_icon)
                setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
                setIconSize(24) // sets the size of the icon.
            }

            val iconStar = iconForm(this) {
                setIcon(R.drawable.star_icon_selected)
                setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
                setIconSize(24) // sets the size of the icon.
            }

            val iconProfile = iconForm(this) {
                setIcon(R.drawable.profile_icon_selected)
                setIconColorRes(R.color.bottom_nav_icon_unselect_color) // sets the [Drawable] of the icon using resource.
                setIconSize(24) // sets the size of the icon.
            }


            val home = BottomMenuItem(this)
                .setIconForm(iconHome)
                .setIcon(R.drawable.home_icon)
                .setIconActiveColor(ContextCompat.getColor(this, R.color.nav_bar_color))
                .build()

            val calendar = BottomMenuItem(this)
                .setIconForm(iconCalendar)
                .setIcon(R.drawable.calendar_icon)
                .setIconActiveColor(ContextCompat.getColor(this, R.color.nav_bar_color))
                .build()

            val search = BottomMenuItem(this)
                .setIconForm(iconSearch)
                .setIcon(R.drawable.search_icon_bottom_nav)
                .setIconActiveColor(ContextCompat.getColor(this, R.color.nav_bar_color))
                .build()

            val star = BottomMenuItem(this)
                .setIconForm(iconStar)
                .setIcon(R.drawable.star_icon)
                .setIconActiveColor(ContextCompat.getColor(this, R.color.nav_bar_color))
                .build()

            val profile = BottomMenuItem(this)
                .setIconForm(iconProfile)
                .setIcon(R.drawable.profile_icon)
                .setIconActiveColor(ContextCompat.getColor(this, R.color.nav_bar_color))
                .setIconColorRes(R.color.bottom_nav_icon_unselect_color)
                .setIconColor(ContextCompat.getColor(this, R.color.bottom_nav_icon_unselect_color))
                .build()
            list.add(home)
            list.add(calendar)
            list.add(search)
            list.add(star)
            list.add(profile)
        }
        navigationItem = list
        return list
    }

    fun getItem(index: Int) = navigationItem[index]
}