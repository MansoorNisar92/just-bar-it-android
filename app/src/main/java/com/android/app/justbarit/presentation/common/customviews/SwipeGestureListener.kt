package com.android.app.justbarit.presentation.common.customviews

import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class SwipeGestureListener(
    private val onSwipe: (Boolean) -> Unit
) : View.OnTouchListener {

    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    private var initialX = 0f
    private var initialY = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                // Consume the move event to prevent the view from scrolling
                view.parent?.requestDisallowInterceptTouchEvent(true)
                return true
            }

            MotionEvent.ACTION_UP -> {
                val deltaX = event.x - initialX
                val deltaY = event.y - initialY

                if (abs(deltaX) > swipeThreshold && abs(deltaX) > swipeVelocityThreshold) {
                    onSwipe.invoke(deltaX < 0) // Pass true for left swipe, false for right swipe
                }
                return true
            }
        }
        return false
    }
}


