package com.android.app.justbarit.presentation.common.customviews.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R

class CalendarAdapter(private val calendarItems: List<Pair<String, String>>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_day_text, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(calendarItems[position])
    }

    override fun getItemCount(): Int = calendarItems.size

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayOfWeekTextView: TextView = itemView.findViewById(R.id.calendarDayOfWeekTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.calendarDateTextView)

        fun bind(item: Pair<String, String>) {
            dayOfWeekTextView.text = item.first
            dateTextView.text = item.second
            // Add click listeners or other logic for each calendar day item
        }
    }
}
