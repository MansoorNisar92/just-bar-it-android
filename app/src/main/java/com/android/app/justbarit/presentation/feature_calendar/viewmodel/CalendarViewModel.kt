package com.android.app.justbarit.presentation.feature_calendar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
import com.android.app.justbarit.domain.model.CalendarItem
import com.android.app.justbarit.domain.model.CalendarItemType
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.domain.model.CategoryType
import com.android.app.justbarit.domain.model.Event
import com.android.app.justbarit.domain.model.EventDetails
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
            CalendarItem("All Events", CalendarItemType.AllFixtures, selected = true),
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

    private fun getHardEventsToday(): ArrayList<EventDetails> {
        return arrayListOf(
            EventDetails(
                "Sasha & John Digweed",
                date = "Saturday at 22:00",
                location = "Liberty Hall Theatre",
                price = "From €27.50",
                eventImage = R.drawable.home_event_three,
                going = "120 are going"
            ),
            EventDetails(
                "Swiftogeddon - The Tylor Swift Club Night",
                date = "Fri,8 Dec 22:30",
                location = "The Grand Social",
                price = "From €8.82",
                eventImage = R.drawable.home_event_one,
                going = "50 are going"
            )
        )
    }

    fun getAllEvents(): ArrayList<EventDetails> {
        return getHardEventsToday()
    }

    fun getMyEvents(): ArrayList<EventDetails> {
        return arrayListOf(
            EventDetails(
                "Monday shots and cocktails",
                date = "Saturday at 13:00",
                location = "Liberty Hall Theatre",
                price = "From €57.50",
                eventImage = R.drawable.bar_cover_dummy,
                going = "120 are going",
                canChat = true
            ),
            EventDetails(
                "Football Weekly Live tour: Dublin (evening 2)",
                date = "Today at 19:30",
                location = "The goat Bar &; Grill",
                price = "From €24.73",
                eventImage = R.drawable.event_image,
                going = "250 are going",
                canChat = true
            )
        )
    }
}