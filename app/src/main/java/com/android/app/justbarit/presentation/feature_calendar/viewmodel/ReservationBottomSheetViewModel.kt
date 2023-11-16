package com.android.app.justbarit.presentation.feature_calendar.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReservationBottomSheetViewModel @Inject constructor() : ViewModel() {

    fun fetchDataForMonth(month: Calendar): List<Pair<String, String>> {
        return generateDataForMonth(month)
    }

    fun fetchTime(): List<String> {
        return generateTime()
    }

    private fun generateTime(): List<String> {
        val militaryHoursList = mutableListOf<String>()

        for (hour in 0..23) {
            val hourString = if (hour < 10) "0$hour" else "$hour"
            val time = "$hourString:00"
            militaryHoursList.add(time)
        }

        return militaryHoursList
    }

    private fun generateDataForMonth(month: Calendar): List<Pair<String, String>> {
        val daysInMonth = month.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthData = mutableListOf<Pair<String, String>>()

        // Set calendar to the first day of the given month
        month.set(Calendar.DAY_OF_MONTH, 1)

        // Generate data for each day of the month
        for (i in 1..daysInMonth) {
            val dayOfWeek =
                month.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            val dayOfMonth = month.get(Calendar.DAY_OF_MONTH).toString()
            val dayPair = Pair(dayOfWeek, dayOfMonth)
            monthData.add(dayPair)
            month.add(Calendar.DAY_OF_MONTH, 1) // Move to the next day
        }

        return monthData
    }
}