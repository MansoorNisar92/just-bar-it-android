package com.android.app.justbarit.presentation.common.ext

import androidx.fragment.app.Fragment
import com.android.app.justbarit.presentation.base.JustBarItBaseActivity

fun Fragment.showProgress() = (requireActivity() as JustBarItBaseActivity).showProgress()
fun Fragment.hideProgress() = (requireActivity() as JustBarItBaseActivity).hideProgress()
