package com.android.app.justbarit.presentation.feature_calendar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
import com.android.app.justbarit.domain.model.CalendarItem
import com.android.app.justbarit.domain.model.CalendarItemType
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.domain.model.CategoryType
import com.android.app.justbarit.domain.model.Event
import com.android.app.justbarit.presentation.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {
    private val _calendarItems = MutableStateFlow<AppState>(AppState.Default)
    val calendarItems: Flow<AppState> get() = _calendarItems.asStateFlow()

    private val _events = MutableStateFlow<AppState>(AppState.Default)
    val events: Flow<AppState> get() = _events.asStateFlow()

    fun getListOfCalendarItems() {
        viewModelScope.launch {
            _calendarItems.emit(AppState.Loading)
            delay(2000)
            val data = getHardCalendarItems()
            _calendarItems.emit(AppState.Success(data))
        }
    }

    fun getListOfEvents() {
        viewModelScope.launch {
            _events.emit(AppState.Loading)
            delay(2000)
            val data = getHardEventsToday()
            _events.emit(AppState.Success(data))
        }
    }


    private fun getHardCalendarItems(): ArrayList<CalendarItem> {
        return arrayListOf(
            CalendarItem("All Fixtures", CalendarItemType.AllFixtures, selected = true),
            CalendarItem("Today", CalendarItemType.Today),
            CalendarItem("Tomorrow", CalendarItemType.Tomorrow),
            CalendarItem("Friday", CalendarItemType.Friday),
            CalendarItem("Saturday", CalendarItemType.Saturday),
            CalendarItem("Sunday", CalendarItemType.Sunday),
            CalendarItem("Monday", CalendarItemType.Monday),
            CalendarItem("Tuesday", CalendarItemType.Tuesday),
            CalendarItem("Wednesday", CalendarItemType.Wednesday),
            CalendarItem("Thursday", CalendarItemType.Thursday),
        )
    }

    private fun getHardEventsToday(): ArrayList<Event> {
        return arrayListOf(
            Event("Friday Night - Fire", "This is event Desc", "11th Nov", "17:00"),
            Event("O’REILLYS - CHEAP MONDAY", "This is event Desc 2", "12th Nov", "13:00"),
            Event("Let's roll it - Dude", "This is event Desc 3", "01st Dec", "23:00"),
            Event("Smoking Lounge and Treat", "This is event Desc 4", "10th Dec", "20:00"),
            Event("Crashing Tonight - Blast", "This is event Desc 5", "31st Dec", "23:30"),
        )
    }
}