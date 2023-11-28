package com.android.app.justbarit.presentation.common.customviews.bottomnavigation

/** OnMenuItemSelectedListener is an interface for listening to the menu item selected. */
fun interface OnMenuItemSelectedListener {

  /** invoked when the menu item is selected. */
  fun onMenuItemSelected(index: Int, bottomMenuItem: BottomMenuItem, fromUser: Boolean)
}
