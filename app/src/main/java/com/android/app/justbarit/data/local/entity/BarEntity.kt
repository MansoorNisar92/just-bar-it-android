package com.android.app.justbarit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.app.justbarit.data.remote.entity.Bar

@Entity(tableName = "bar")
data class BarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var address: String?,
    var disco: String?,
    var food: String?,
    var gameNight: String?,
    var latLng: String?,
    var liveMusic: String?,
    var name: String?,
    var petFriendly: String?,
    var phone: String?,
    var rating: Double?,
    var smokingArea: String?,
    var sports: String?,
    var terraceRooftop: String?,
    var web: String?
)

fun List<BarEntity>.toBarModelList(): List<Bar> {
    val list = mutableListOf<Bar>()
    forEach {
        list.add(it.toBarModel())
    }
    return list
}

fun BarEntity.toBarModel(): Bar {
    return Bar(
        address,
        disco,
        food,
        gameNight,
        latLng,
        liveMusic,
        name,
        petFriendly,
        phone,
        rating,
        smokingArea,
        sports,
        terraceRooftop,
        web
    )
}