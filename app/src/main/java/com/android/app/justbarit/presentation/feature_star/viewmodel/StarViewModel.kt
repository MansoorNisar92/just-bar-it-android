package com.android.app.justbarit.presentation.feature_star.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
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
class StarViewModel @Inject constructor() : ViewModel() {

    private val _favouriteEvents = MutableStateFlow<AppState>(AppState.Default)
    val favouriteEvents: Flow<AppState> get() = _favouriteEvents.asStateFlow()


    fun getListOfFavouriteEvents() {
        viewModelScope.launch {
            _favouriteEvents.emit(AppState.Loading)
            delay(2000)
            val data = getHardFavouriteEvents()
            _favouriteEvents.emit(AppState.Success(data))
        }
    }

    private fun getHardFavouriteEvents(): ArrayList<EventDetails> {
        return arrayListOf(
            EventDetails(
                "Swiftogeddon - The Tylor Swift Club Night",
                date = "Fri,8 Dec 22:30",
                location = "The Grand Social",
                price = "From €8.82",
                eventImage = R.drawable.home_event_one,
                markedFavourite = true
            ),
            EventDetails(
                "The Gang's All Here Extra: Live at Liberty Hall",
                date = "Fri,8 Dec 19:00",
                location = "Liberty Hall Theatre",
                price = "From €31.00",
                eventImage = R.drawable.home_event_two,
                markedFavourite = true
            )
        )
    }
}