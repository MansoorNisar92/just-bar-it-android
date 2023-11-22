package com.android.app.justbarit.domain.model

import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.presentation.common.ext.default
import com.android.app.justbarit.presentation.common.ext.toBooleanOrFalse

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
    var reviewCount: Int = 0,
    var amenities: ArrayList<Amenity> = arrayListOf<Amenity>()
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
        amenities = this.getAmenities()
        )
}

fun Bar.getAmenities(): ArrayList<Amenity> {
    val list = arrayListOf<Amenity>()
    list.add(Amenity(this.disco?.toBooleanOrFalse(), AmenityType.Disco))
    list.add(Amenity(this.food?.toBooleanOrFalse(), AmenityType.Food))
    list.add(Amenity(this.gameNight?.toBooleanOrFalse(), AmenityType.GameNight))
    list.add(Amenity(this.liveMusic?.toBooleanOrFalse(), AmenityType.LiveMusic))
    list.add(Amenity(this.petFriendly?.toBooleanOrFalse(), AmenityType.PetFriendly))
    list.add(Amenity(this.smokingArea?.toBooleanOrFalse(), AmenityType.SmokingArea))
    list.add(Amenity(this.sports?.toBooleanOrFalse(), AmenityType.Sports))
    list.add(Amenity(this.terraceRooftop?.toBooleanOrFalse(), AmenityType.TerraceRoofTop))
    return list
}

