package com.android.app.justbarit.domain.model

data class Bar(
    var barName: String,
    var barRating: Double,
    var barFeedback: String = "",
    var distance: Double = 1.0,
    var hasWifi: Boolean = false,
    var hasDineIn: Boolean = false,
    var hasDrink: Boolean = false,
    var hasBedRoom: Boolean = false,
    var hasDance: Boolean = false,
    var hasFreeEntry: Boolean = false,
    var reviewCount: Int = 0
)

