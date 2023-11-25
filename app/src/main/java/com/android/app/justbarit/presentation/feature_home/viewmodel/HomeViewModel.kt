package com.android.app.justbarit.presentation.feature_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
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
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _categories = MutableStateFlow<AppState>(AppState.Default)
    val categories: Flow<AppState> get() = _categories.asStateFlow()

    private val _eventsToday = MutableStateFlow<AppState>(AppState.Default)
    val eventsToday: Flow<AppState> get() = _eventsToday.asStateFlow()

    fun getListOfCategories() {
        viewModelScope.launch {
            _categories.emit(AppState.Loading)
            delay(2000)
            val data = getHardCodeCategories()
            _categories.emit(AppState.Success(data))
        }
    }

    fun getListOfEventsToday() {
        viewModelScope.launch {
            _eventsToday.emit(AppState.Loading)
            delay(2000)
            val data = getHardEventsToday()
            _eventsToday.emit(AppState.Success(data))
        }
    }

    private fun getHardCodeCategories(): ArrayList<Category> {
        return arrayListOf(
            Category("Food", R.drawable.food_icon, true, CategoryType.Food),
            Category("Jazz", R.drawable.jazz_icon, false, CategoryType.Jazz),
            Category("Karaoke", R.drawable.icon_karaoke, false, CategoryType.Karaoke),
            Category("Sports", R.drawable.sport_icon, false, CategoryType.Sport)
        )
    }


    private fun getHardEventsToday(): ArrayList<EventDetails> {
        return arrayListOf(
            EventDetails(
                "Swiftogeddon - The Tylor Swift Club Night",
                date = "Fri,8 Dec 22:30",
                location = "The Grand Social",
                price = "From €8.82",
                eventImage = R.drawable.home_event_one
            ),
            EventDetails(
                "The Gang's All Here Extra: Live at Liberty Hall",
                date = "Fri,8 Dec 19:00",
                location = "Liberty Hall Theatre",
                price = "From €31.00",
                eventImage = R.drawable.home_event_two
            )
        )
    }
}