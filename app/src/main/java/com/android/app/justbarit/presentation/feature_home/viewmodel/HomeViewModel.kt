package com.android.app.justbarit.presentation.feature_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
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