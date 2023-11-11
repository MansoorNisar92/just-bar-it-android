package com.android.app.justbarit.presentation.common.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.android.app.justbarit.databinding.ItemEventTodayBinding

class EventTodayItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleRes: Int = 0
) : CardView(context, attributeSet, defStyleRes) {

    private val binding: ItemEventTodayBinding = ItemEventTodayBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        this.layoutParams = layoutParams
    }
    fun addEvent(event: String) {
        binding.eventNameTextView.text = event
    }


}