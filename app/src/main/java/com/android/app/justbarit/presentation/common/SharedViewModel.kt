package com.android.app.justbarit.presentation.common

import androidx.lifecycle.ViewModel
import com.android.app.justbarit.domain.model.Bar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private var _bar: Bar? = null
    fun setSelectedBar(selectedBar: Bar) {
        _bar = selectedBar
    }

    fun getSelectedBar() = _bar
}