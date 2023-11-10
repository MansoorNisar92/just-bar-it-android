package com.android.app.justbarit.presentation.common.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.clickToAction(action: () -> Unit = {}) {
    setOnClickListener {
        hideKeyboard()
        action()
    }
}
fun View.hideKeyboard() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
        windowToken,
        0
    )
}
