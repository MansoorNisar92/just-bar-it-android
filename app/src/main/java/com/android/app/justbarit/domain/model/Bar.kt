package com.android.app.justbarit.domain.model

import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.presentation.common.ext.default

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

fun List<Bar>.convertRemoteBarToLocalBarsList(): List<com.android.app.justbarit.domain.model.Bar> {
    val list = mutableListOf<com.android.app.justbarit.domain.model.Bar>()
    forEach {
        list.add(it.toLocalBar())
    }
    return list
}

fun Bar.toLocalBar(): com.android.app.justbarit.domain.model.Bar {
    return Bar(
        barName = name ?: "",
        barRating = rating ?: 0.0,

        )
}

