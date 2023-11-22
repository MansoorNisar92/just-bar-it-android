package com.android.app.justbarit.data.remote.entity


import com.android.app.justbarit.data.local.entity.BarEntity
import com.android.app.justbarit.presentation.common.ext.default

data class Bar(
    var address: String?  = null,
    var disco: String?  = null,
    var food: String?  = null,
    var gameNight: String?  = null,
    var latLng: String?  = null,
    var liveMusic: String?  = null,
    var name: String?  = null,
    var petFriendly: String?  = null,
    var phone: String?  = null,
    var rating: Double?  = null,
    var smokingArea: String?  = null,
    var sports: String?  = null,
    var terraceRooftop: String?  = null,
    var web: String?  = null
) {
    constructor(map: Map<String, String>) : this() {
        map.forEach { entry ->
            when (entry.key) {
                "address" -> address = entry.value
                "disco" -> disco = entry.value
                "food" -> food = entry.value
                "gameNight" -> gameNight = entry.value
                "latLng" -> latLng = entry.value
                "liveMusic" -> liveMusic = entry.value
                "name" -> name = entry.value
                "perFriendly" -> petFriendly = entry.value
                "phone" -> phone = entry.value
                "smokingArea" -> smokingArea = entry.value
                "sports" -> sports = entry.value
                "terraceRooftop" -> terraceRooftop = entry.value
                "rating" -> rating = entry.value as Double
                "web" -> web = entry.value

            }
        }
    }
}

fun List<Bar>.toBarEntityList(): List<BarEntity> {
    val list = mutableListOf<BarEntity>()
    forEach {
        if (it.address?.isNotEmpty().default) {
            list.add(it.toBarEntity())
        }
    }
    return list
}

fun Bar.toBarEntity(): BarEntity {
    return BarEntity(
        address = address,
        disco = disco,
        food = food,
        gameNight = gameNight,
        latLng = latLng,
        liveMusic = liveMusic,
        name = name,
        petFriendly = petFriendly,
        phone = phone,
        rating = rating,
        smokingArea = smokingArea,
        sports = sports,
        terraceRooftop = terraceRooftop,
        web = web
    )
}