package com.android.app.justbarit.presentation.feature_calendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.app.justbarit.R
import com.android.app.justbarit.databinding.ItemCalednarBinding
import com.android.app.justbarit.domain.model.CalendarItem
import com.android.app.justbarit.presentation.common.ext.clickToAction
import com.android.app.justbarit.presentation.common.ext.propagationAnimation

class CalendarItemAdapter constructor(
    calendarItems: ArrayList<CalendarItem>,
    val context: Context
) :
    RecyclerView.Adapter<CalendarItemAdapter.CalendarItemViewHolder>() {

    private var calendarItemsList = calendarItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemViewHolder {
        val binding =
            ItemCalednarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarItemViewHolder, position: Int) {
        holder.bind(calendarItem = calendarItemsList[position], context)
    }

    override fun getItemCount(): Int {
        return calendarItemsList.size
    }

    class CalendarItemViewHolder(private val binding: ItemCalednarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(calendarItem: CalendarItem, context: Context) {
            binding.calendarDayTextView.text = calendarItem.dayName
            if (calendarItem.selected) {
                binding.calendarDayTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.underLine.visibility = View.VISIBLE
                binding.underLine.propagationAnimation()
            } else {
                binding.calendarDayTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.eventTodayCardColor
                    )
                )
                binding.underLine.visibility = View.GONE
            }
            binding.calendarDayTextView.clickToAction {
                calendarItemClick(calendarItem)
            }
        }
    }

    fun setCalendarItems(newItems: ArrayList<CalendarItem>) {
        calendarItemsList = newItems
        notifyDataSetChanged()
    }


    fun updateCalendarItem(updatedCategory: CalendarItem) {
        calendarItemsList.forEach { it.selected = false }
        val index = calendarItemsList.indexOf(updatedCategory)
        calendarItemsList[index].selected = true
        notifyDataSetChanged()
    }

    fun updateItemAtPosition(position: Int){
        calendarItemsList.forEach { it.selected = false }
        calendarItemsList[position].selected = true
        notifyDataSetChanged()
    }
    fun selectedItemPosition(): Int {
        return calendarItemsList.indexOfFirst { it.selected }
    }
}

var calendarItemClick: (CalendarItem) -> Unit = {

}