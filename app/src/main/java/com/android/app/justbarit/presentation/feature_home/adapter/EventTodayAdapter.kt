package com.android.app.justbarit.presentation.feature_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.databinding.ItemEventTodayBinding
import com.android.app.justbarit.presentation.common.ext.clickToAction

class EventTodayAdapter constructor(categories: ArrayList<String>) :
    RecyclerView.Adapter<EventTodayAdapter.EventTodayViewHolder>() {

    private var eventsList = categories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventTodayViewHolder {
        val binding =
            ItemEventTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventTodayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventTodayViewHolder, position: Int) {
        holder.bind(event = eventsList[position], position)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventTodayViewHolder(private val binding: ItemEventTodayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: String, position: Int) {
            binding.eventNameTextView.text = event
            binding.apply {
               /* eventArrowLeftLayout.clickToAction {
                    nextEvent(position, Direction.Left)
                }

                eventArrowRightLayout.clickToAction {
                    nextEvent(position, Direction.Right)
                }*/
            }
        }
    }

    fun setEventsToday(newEvents: ArrayList<String>) {
        eventsList = newEvents
        notifyDataSetChanged()
    }

}

var nextEvent: (Int, Direction) -> Unit = { _: Int, _: Direction ->

}


sealed class Direction {
    data object Left : Direction()
    data object Right : Direction()
}