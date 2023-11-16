package com.android.app.justbarit.presentation.common.customviews.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ItemCalendarDayTextBinding
import com.android.app.justbarit.domain.model.CalendarDate
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.propagationAnimation

class CalendarAdapter(calendarItems: List<CalendarDate>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var calendarDates = calendarItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCalendarDayTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(item = calendarDates[position])
    }

    override fun getItemCount(): Int = calendarDates.size

    inner class CalendarViewHolder(private val binding: ItemCalendarDayTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarDate) {
            binding.apply {
                calendarDayOfWeekTextView.text = item.date.first
                calendarDateTextView.text = item.date.second

                if (item.selectedDate){
                    calendarDayLayout.setBackgroundResource(R.drawable.date_or_time_selected_background)
                    calendarDateTextView.setTextColor(ContextCompat.getColor(calendarDateTextView.context, R.color.white))
                    calendarDayOfWeekTextView.setTextColor(ContextCompat.getColor(calendarDayOfWeekTextView.context, R.color.white))
                    calendarDayLayout.propagationAnimation()
                }else{
                    calendarDayLayout.setBackgroundResource(0)
                    calendarDateTextView.setTextColor(ContextCompat.getColor(calendarDateTextView.context, R.color.black))
                    calendarDayOfWeekTextView.setTextColor(ContextCompat.getColor(calendarDayOfWeekTextView.context, R.color.black))
                }

                calendarDayLayout.clickToAction {
                    calendarDayClick(item)
                }
            }
        }
    }

    fun updateDate(updateDate: CalendarDate) {
        calendarDates.forEach { it.selectedDate = false }
        val index = calendarDates.indexOf(updateDate)
        calendarDates[index].selectedDate = true
        notifyDataSetChanged()
    }
}

var calendarDayClick: (CalendarDate) -> Unit = {

}
