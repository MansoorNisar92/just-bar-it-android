package com.android.app.justbarit.presentation.feature_search.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
import com.android.app.justbarit.domain.model.Bar
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.domain.model.CategoryType
import com.android.app.justbarit.presentation.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _bars = MutableStateFlow<AppState>(AppState.Default)
    val bars: Flow<AppState> get() = _bars.asStateFlow()


    fun getListOfBars() {
        viewModelScope.launch {
            _bars.emit(AppState.Loading)
            delay(4000)
            val data = getHardCodeBars()
            _bars.emit(AppState.Success(data))
        }
    }

    private fun getHardCodeBars(): ArrayList<Bar> {
        return arrayListOf(
            Bar(barName = "The Brazen Head", barRating = 10.0, distance = 10.0, hasDineIn = true, hasBedRoom = true, hasDrink = true, hasWifi = true),
            Bar(barName = "The Temple Bar Pub", barRating = 6.0, distance = 4.0, hasDineIn = true, hasWifi = true),
            Bar(barName = "Bar Rua", barRating = 5.0, distance = 4.0, hasWifi = true),
            Bar(barName = "The Long Hall", barRating = 7.0, distance = 2.0,  hasWifi = true),
            Bar(barName = "Porterhouse Temple Bar", barRating = 8.0, distance = 5.0, hasDineIn = true, hasDrink = true),
            Bar(barName = "O'Donoghues Bar", barRating = 4.0, distance = 1.0),
            Bar(barName = "P. Duggan's", barRating = 6.0, distance = 3.0, hasDineIn = true, hasWifi = true, hasBedRoom = true),
        )
    }
}