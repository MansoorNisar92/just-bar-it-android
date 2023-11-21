package com.android.app.justbarit.data.remote.entity


import com.fasterxml.jackson.annotation.JsonProperty

data class Bar(
    @JsonProperty("Address")
    var address: String?,
    @JsonProperty("Disco")
    var disco: String?,
    @JsonProperty("Food")
    var food: String?,
    @JsonProperty("Game night")
    var gameNight: String?,
    @JsonProperty("LatLng")
    var latLng: String?,
    @JsonProperty("Live music")
    var liveMusic: String?,
    @JsonProperty("Name")
    var name: String?,
    @JsonProperty("Pet friendly")
    var petFriendly: String?,
    @JsonProperty("Phone")
    var phone: String?,
    @JsonProperty("Rating")
    var rating: Double?,
    @JsonProperty("Smoking Area")
    var smokingArea: String?,
    @JsonProperty("Sports")
    var sports: String?,
    @JsonProperty("Terrace/Rooftop")
    var terraceRooftop: String?,
    @JsonProperty("Web")
    var web: String?
)