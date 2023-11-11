package com.android.app.justbarit.presentation

sealed class AppState {
    class Success<T>(val response : T) : AppState()
    class Failure<T>(val response : T) : AppState()
    object Loading : AppState()
    object Default : AppState()
}
