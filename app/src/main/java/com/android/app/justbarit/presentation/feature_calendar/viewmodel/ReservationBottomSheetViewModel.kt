package com.android.app.justbarit.presentation.feature_calendar.viewmodel

import androidx.lifecycle.ViewModel
import com.android.app.justbarit.domain.model.CalendarDate
import com.android.app.justbarit.domain.model.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReservationBottomSheetViewModel @Inject constructor() : ViewModel() {

    private var selectedMonth: LocalDate = LocalDate.now()
    private var currentMonth: LocalDate = selectedMonth

    fun getCurrentMonth() = currentMonth
    fun getSelectedMonth() = selectedMonth

    fun updateSelectedMonth(newSelectedMonth: LocalDate) {
        selectedMonth = newSelectedMonth
    }

    fun fetchDataForMonth(month: LocalDate): List<CalendarDate> {
        return generateDataForMonth(month)
    }

    fun fetchTime(): List<Time> {
        return generateTime()
    }

    private fun generateTime(): List<Time> {
        val militaryHoursList = mutableListOf<Time>()

        for (hour in 0..23) {
            val hourString = if (hour < 10) "0$hour" else "$hour"
            val time = Time("$hourString:00", false)
            militaryHoursList.add(time)
        }

        return militaryHoursList
    }

    private fun generateDataForMonth(month: LocalDate): List<CalendarDate> {
        val daysInMonth = month.month.length(month.isLeapYear)
        val monthData = mutableListOf<CalendarDate>()
        val firstDayOfMonth = month.withDayOfMonth(1)
        var currentDay = firstDayOfMonth
        for (i in 1..daysInMonth) {
            val dayOfWeek =
                currentDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            val dayOfMonth = currentDay.dayOfMonth.toString()
            val dayPair = Pair(dayOfWeek, dayOfMonth)
            monthData.add(CalendarDate(dayPair, false))
            currentDay = currentDay.plusDays(1) // Move to the next day
        }

        return monthData
    }
}