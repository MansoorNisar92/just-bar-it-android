package com.android.app.justbarit.domain.model

data class EventDetails(
    var title: String? = null,
    var date: String? = null,
    var location: String? = null,
    var going: String? = null,
    var price: String? = null,
    var eventImage: Int? = 0,
    var canChat: Boolean? = false,
    var markedFavourite: Boolean? = false,
    var almostFull: Boolean? = false,
    var salesEndSoon: Boolean? = false
)
