package com.android.app.justbarit.data.remote.entity


import com.fasterxml.jackson.annotation.JsonProperty

data class BarsResponse(
    @JsonProperty("bars")
    var bars: List<Bar?>?,
    @JsonProperty("meta")
    var meta: Meta?
)