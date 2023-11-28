package com.android.app.justbarit.presentation.feature_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.domain.model.Bar
import com.android.app.justbarit.domain.model.convertRemoteBarToLocalBarsList
import com.android.app.justbarit.domain.usecases.GetBarsFromLocalDatabaseUseCase
import com.android.app.justbarit.presentation.AppState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
}