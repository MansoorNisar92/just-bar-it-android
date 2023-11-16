package com.android.app.justbarit.presentation.common.customviews.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R

class TimeAdapter(private val timeItems: List<String>) :
    RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_calendar_time_text, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(timeItems[position])
    }

    override fun getItemCount(): Int = timeItems.size

    inner class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val calendarTimeTextView: TextView =
            itemView.findViewById(R.id.calendarTimeTextView)

        fun bind(item: String) {
            calendarTimeTextView.text = item
        }
    }
}
