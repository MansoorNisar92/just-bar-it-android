package com.android.app.justbarit.presentation.common.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.android.app.justbarit.databinding.ItemEventTodayBinding
import com.android.app.justbarit.domain.model.Event

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
    fun addEvent(event: Event) {
        binding.eventNameTextView.text = event.eventName
        binding.eventDescTextView.text = event.eventDesc
        binding.eventDateTextView.text = event.eventDate
        binding.eventTimeTextView.text = event.evenTime
    }


}