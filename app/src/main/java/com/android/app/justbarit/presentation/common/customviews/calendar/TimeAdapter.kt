package com.android.app.justbarit.presentation.common.customviews.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ItemCalendarTimeTextBinding
import com.android.app.justbarit.domain.model.CalendarItem
import com.android.app.justbarit.domain.model.Time
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.propagationAnimation

class TimeAdapter(timeItems: List<Time>) :
    RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    private var timesList = timeItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding =
            ItemCalendarTimeTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(time = timesList[position])
    }

    override fun getItemCount(): Int = timesList.size

    inner class TimeViewHolder(private val binding: ItemCalendarTimeTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(time: Time) {
            binding.apply {
                calendarTimeTextView.text = time.time
                if (time.selectedTime) {
                    calendarTimeLayout.setBackgroundResource(R.drawable.date_or_time_selected_background)
                    calendarTimeTextView.setTextColor(ContextCompat.getColor(calendarTimeTextView.context, R.color.white))
                    calendarTimeLayout.propagationAnimation()
                } else {
                    calendarTimeLayout.setBackgroundResource(0)
                    calendarTimeTextView.setTextColor(ContextCompat.getColor(calendarTimeTextView.context, R.color.black))
                }
                calendarTimeTextView.clickToAction {
                    timeClick(time)
                }
            }
        }
    }

    fun updateTime(updatedTime: Time) {
        timesList.forEach { it.selectedTime = false }
        val index = timesList.indexOf(updatedTime)
        timesList[index].selectedTime = true
        notifyDataSetChanged()
    }
}

var timeClick: (Time) -> Unit = {

}
