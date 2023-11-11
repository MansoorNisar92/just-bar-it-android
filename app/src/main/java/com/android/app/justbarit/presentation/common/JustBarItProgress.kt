package com.android.app.justbarit.presentation.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import com.android.app.justbarit.R
import java.lang.ref.WeakReference

class JustBarItProgress constructor(context: Context) {

    private var contextWeak: WeakReference<Context> = WeakReference(context)
    private var isShowing = false
    private var decorView: ViewGroup? = null
    private var rootView: ViewGroup? = null
    private var layoutParams: RelativeLayout.LayoutParams? = null

    init {
        initViews((context as Activity).window.decorView as ViewGroup, R.layout.loader)
    }

    private fun initViews(decorView: ViewGroup?, @LayoutRes loaderLayout: Int) {
        val context = contextWeak.get() ?: return
        this.decorView = decorView
        val layoutInflater = LayoutInflater.from(context)
        rootView = layoutInflater.inflate(loaderLayout, null, false) as ViewGroup
        rootView?.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun isShowing(): Boolean {
        return rootView?.parent != null || isShowing
    }

    fun dismiss() {
        if (!isShowing()) {
            return
        }
        decorView?.removeView(rootView)
        isShowing = false
    }

    private fun setCancelable(isCancelable: Boolean = true) {
        val view = rootView?.findViewById<View>(R.id.loaderContainer)
        if (isCancelable) {
            view?.setOnTouchListener(onCancelableTouchListener)
        } else {
            view?.setOnTouchListener(null)
        }
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    @SuppressLint("ClickableViewAccessibility")
    private val onCancelableTouchListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            dismiss()
            setCancelable(false)
        }
        false
    }

    fun show() {
        if (isShowing()) {
            return
        }
        onAttached()
    }

    private fun onAttached() {
        isShowing = true
        decorView?.addView(rootView)
    }

}