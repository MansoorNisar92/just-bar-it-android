package com.android.app.justbarit.presentation.common.customviews.bottomnavigation


data class BottomMenuItemViewConfig(
  val index: Int,
  val bottomMenuItem: BottomMenuItem,
  val bottomBarFlavor: BottomBarFlavor,
  val animation: BottomMenuAnimation,
  val duration: Long
)
