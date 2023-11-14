package com.android.app.justbarit.domain.model

data class EventDetails(
    var eventName: String,
    var eventDate: String,
    var evenTime: String,
    var homeTeam: Team,
    var awayTeam: Team
)

data class Team(
    var teamName: String,
    var teamBadge: Int
)
