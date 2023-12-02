package com.android.app.justbarit.presentation.splash.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.domain.usecases.CheckBarExistsInLocalDatabaseUseCase
import com.android.app.justbarit.domain.usecases.GetBarsFromLocalDatabaseUseCase
import com.android.app.justbarit.domain.usecases.SetBarsInLocalDatabaseUseCase
import com.android.app.justbarit.presentation.AppState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getBarsFromLocalDatabaseUseCase: GetBarsFromLocalDatabaseUseCase,
    private val setBarsInLocalDatabaseUseCase: SetBarsInLocalDatabaseUseCase,
    private val checkBarExistsInLocalDatabaseUseCase: CheckBarExistsInLocalDatabaseUseCase
) : ViewModel() {
    private val _barsResponse = MutableStateFlow<AppState>(AppState.Default)
    val barsResponse: Flow<AppState> get() = _barsResponse.asStateFlow()
    fun fetchBars(context: Context, resourceId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkIfBarsExistsInDatabase()) {
                loadBarsFromDatabase()
            } else {
                val bars = readBarsFromRawResource(context, resourceId)
                viewModelScope.launch(Dispatchers.IO) {
                    setBarsInLocalDatabaseUseCase(bars)
                    _barsResponse.emit(AppState.Success(bars))
                }
            }
        }

    }

    private fun readBarsFromRawResource(context: Context, resourceId: Int): List<Bar> {
        val inputStream = context.resources.openRawResource(resourceId)
        val jsonText = inputStream.bufferedReader().use { it.readText() }

        val gson = Gson()
        val jsonObject = gson.fromJson(jsonText, Map::class.java)
        val barsListType = object : TypeToken<List<Bar>>() {}.type

        val barsJsonArray = jsonObject["bars"]
        val barsJsonString = gson.toJson(barsJsonArray)
        return gson.fromJson(barsJsonString, barsListType)
    }


    private fun loadBarsFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val barsList = getBarsFromLocalDatabaseUseCase
                _barsResponse.emit(AppState.Success(barsList))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun checkIfBarsExistsInDatabase(): Boolean {
        return checkBarExistsInLocalDatabaseUseCase()
    }

}