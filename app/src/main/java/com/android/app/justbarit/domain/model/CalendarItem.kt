package com.android.app.justbarit.domain.model

data class CalendarItem(
    var dayName: String,
    var calendarItemType: CalendarItemType,
    var selected: Boolean = false
)

sealed class CalendarItemType {
    data object AllFixtures : CalendarItemType()
    data object Today : CalendarItemType()
    data object Tomorrow : CalendarItemType()
    data object Friday : CalendarItemType()
    data object Saturday : CalendarItemType()
    data object Sunday : CalendarItemType()
    data object Monday : CalendarItemType()
    data object Tuesday : CalendarItemType()
    data object Wednesday : CalendarItemType()
    data object Thursday : CalendarItemType()
}
