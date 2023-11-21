package com.android.app.justbarit.data.remote.entity


import com.fasterxml.jackson.annotation.JsonProperty

data class Meta(
    @JsonProperty("flushDB")
    var flushDB: Boolean?
)