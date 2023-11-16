package com.android.app.justbarit.presentation.feature_calendar.viewmodel

import androidx.lifecycle.ViewModel
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

    fun fetchDataForMonth(month: LocalDate): List<Pair<String, String>> {
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

    private fun generateDataForMonth(month: LocalDate): List<Pair<String, String>> {
        val daysInMonth = month.month.length(month.isLeapYear)
        val monthData = mutableListOf<Pair<String, String>>()

        // Set LocalDate to the first day of the given month
        val firstDayOfMonth = month.withDayOfMonth(1)

        // Generate data for each day of the month
        var currentDay = firstDayOfMonth
        for (i in 1..daysInMonth) {
            val dayOfWeek =
                currentDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            val dayOfMonth = currentDay.dayOfMonth.toString()
            val dayPair = Pair(dayOfWeek, dayOfMonth)
            monthData.add(dayPair)
            currentDay = currentDay.plusDays(1) // Move to the next day
        }

        return monthData
    }
}