package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventTodayBinding
import com.android.app.justbarit.domain.model.Event
import com.android.app.justbarit.presentation.common.ext.clickToAction

class CalendarAdapter constructor(categories: ArrayList<Event>) :
    RecyclerView.Adapter<CalendarAdapter.EventTodayViewHolder>() {

    private var eventsList = categories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTodayViewHolder {
        val binding =
            ItemEventTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventTodayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventTodayViewHolder, position: Int) {
        holder.bind(event = eventsList[position])
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventTodayViewHolder(private val binding: ItemEventTodayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.apply {
                eventNameTextView.text = event.eventName
                eventCardView.clickToAction {
                    eventClick(event)
                }
            }
        }
    }

    fun setEventsToday(newEvents: ArrayList<Event>) {
        eventsList = newEvents
        notifyDataSetChanged()
    }

}

var eventClick: (Event) -> Unit = {

}


sealed class Direction {
    data object Left : Direction()
    data object Right : Direction()
}