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
import com.android.app.justbarit.domain.model.Review
import com.android.app.justbarit.domain.model.Team
import com.android.app.justbarit.presentation.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarDetailsViewModel @Inject constructor() : ViewModel() {
    private val _events = MutableStateFlow<AppState>(AppState.Default)
    val events: Flow<AppState> get() = _events.asStateFlow()

    private val _reviews = MutableStateFlow<AppState>(AppState.Default)
    val reviews: Flow<AppState> get() = _reviews.asStateFlow()


    fun getListOfEvents() {
        viewModelScope.launch {
            _events.emit(AppState.Loading)
            delay(2000)
            val data = getHardEventsToday()
            _events.emit(AppState.Success(data))
        }
    }

    fun getListOfReviews() {
        viewModelScope.launch {
            _reviews.emit(AppState.Loading)
            delay(1000)
            val data = getHardcodeReviews()
            _reviews.emit(AppState.Success(data))
        }
    }

    private fun getHardEventsToday(): ArrayList<EventDetails> {
        return arrayListOf(
            EventDetails(
                "Carabao Cup",
                "11th Nov",
                "17:00",
                Team("Man. City", 0),
                Team("Man. Utd", 0)
            ),
            EventDetails(
                "Premier League", "20th Nov", "20:00", Team("Chelsea", 0),
                Team("Liverpool", 0)
            ),
            EventDetails(
                "Champions League", "01st Dec", "23:00",
                Team("Man. City", 0),
                Team("Barcelona", 0)
            ),
            EventDetails(
                "FA Cup", "10th Dec", "20:00",
                Team("Man. City", 0),
                Team("Man. Utd", 0)
            ),
            EventDetails(
                "Premier League", "31st Dec", "23:30",
                Team("Man. City", 0),
                Team("Spurs", 0)
            ),
        )
    }

    private fun getHardcodeReviews(): ArrayList<Review> {
        return arrayListOf(
            Review("Lauren", "This is the best Bar", 5.0f),
            Review("Ki", "Bar is good, lights can be better", 3.0f),
            Review("Jason", "Drinks can have more options and also lights", 4.0f),
            Review("Krystal", "Did not like the decor", 1.0f),
            Review("Solomon", "Drinks are good, too much noise", 2.0f),
        )
    }
}