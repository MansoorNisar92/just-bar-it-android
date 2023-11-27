package com.android.app.justbarit.presentation.common.ext

import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.android.app.justbarit.R
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity
import com.google.android.material.snackbar.Snackbar


fun Fragment.showProgress() = (requireActivity() as JustBarItBaseActivity).showProgress()
fun Fragment.hideProgress() = (requireActivity() as JustBarItBaseActivity).hideProgress()

fun Fragment.popBackStack() {
    val navController = NavHostFragment.findNavController(this)
    navController.popBackStack()
}

fun Fragment.navigate(destination: Int) {
    val navOptions = navOptions(destination)
    val navController = NavHostFragment.findNavController(this)
    navController.navigate(destination, args = null, navOptions = navOptions)
}

private fun navOptions(destination: Int) = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in)
    .setExitAnim(R.anim.slide_out)
    .setPopEnterAnim(R.anim.slide_in)
    .setPopExitAnim(R.anim.slide_out)
    .setPopUpTo(destination, true)
    .build()

fun Fragment.showSnackBar(
    message: String?,
    duration: Int = Snackbar.LENGTH_LONG
) =
    message?.let {
        view?.let {
            val color = colorStateList(R.color.nav_bar_color)
            val textColor = colorStateList(R.color.white)
            Snackbar.make(it, message, duration).apply {
                setBackgroundTintList(color)
                setTextColor(textColor)
            }.show()
        }
    }

fun Fragment.colorStateList(@ColorRes color: Int): ColorStateList =
    AppCompatResources.getColorStateList(requireContext(), color)
