package com.android.app.justbarit.presentation.feature_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.R
import com.android.app.justbarit.domain.model.Bar
import com.android.app.justbarit.domain.model.Category
import com.android.app.justbarit.domain.model.CategoryType
import com.android.app.justbarit.domain.model.convertRemoteBarToLocalBarsList
import com.android.app.justbarit.domain.usecases.GetBarsFromLocalDatabaseUseCase
import com.android.app.justbarit.presentation.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getBarsFromLocalDatabaseUseCase: GetBarsFromLocalDatabaseUseCase) : ViewModel() {
    private val _bars = MutableStateFlow<AppState>(AppState.Default)
    val bars: Flow<AppState> get() = _bars.asStateFlow()


    fun getListOfBars() {
        viewModelScope.launch(Dispatchers.IO) {
            _bars.emit(AppState.Loading)
            try {
                val barsList = getBarsFromLocalDatabaseUseCase.invoke()
                _bars.emit(AppState.Success(barsList.convertRemoteBarToLocalBarsList()))
            } catch (e: Exception) {
                _bars.emit(AppState.Failure(e.message))
            }
        }

    }

    private fun getHardCodeBars(): ArrayList<Bar> {
        return arrayListOf(
            Bar(barName = "The Brazen Head", barRating = 10.0, distance = 10.0, hasDineIn = true, hasBedRoom = true, hasDrink = true, hasWifi = true, hasDance = true, hasFreeEntry = true, reviewCount = 1000),
            Bar(barName = "The Temple Bar Pub", barRating = 6.0, distance = 4.0, hasDineIn = true, hasWifi = true, hasFreeEntry = true),
            Bar(barName = "Bar Rua", barRating = 5.0, distance = 4.0, hasWifi = true, hasDrink = true),
            Bar(barName = "The Long Hall", barRating = 7.0, distance = 2.0,  hasWifi = true, hasDance = true),
            Bar(barName = "Porterhouse Temple Bar", barRating = 8.0, distance = 5.0, hasDineIn = true, hasDrink = true, reviewCount = 40),
            Bar(barName = "O'Donoghues Bar", barRating = 4.0, distance = 1.0, hasFreeEntry = true, hasDance = true, reviewCount = 100),
            Bar(barName = "P. Duggan's", barRating = 6.0, distance = 3.0, hasDineIn = true, hasWifi = true, hasBedRoom = true, hasDance = true, hasFreeEntry = true, reviewCount = 5000),
        )
    }
}