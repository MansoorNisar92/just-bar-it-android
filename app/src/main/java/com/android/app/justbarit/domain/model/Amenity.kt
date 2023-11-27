package com.android.app.justbarit.domain.model

data class Amenity(
    var amenityProvided: Boolean? = false,
    var amenityType: AmenityType
)

sealed class AmenityType {
    data object Disco : AmenityType()
    data object Drinks : AmenityType()
    data object Food : AmenityType()
    data object GameNight : AmenityType()
    data object LiveMusic : AmenityType()
    data object PetFriendly : AmenityType()
    data object SmokingArea : AmenityType()
    data object Sports : AmenityType()
    data object TerraceRoofTop : AmenityType()
    data object Wifi : AmenityType()
}

