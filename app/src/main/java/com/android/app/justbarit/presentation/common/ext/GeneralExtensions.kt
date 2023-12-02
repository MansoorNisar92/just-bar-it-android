package com.android.app.justbarit.presentation.common.ext


fun getRatingInDesc(rating: Int): String {
    val ratingsMap = mapOf(
        1 to "Very Poor",
        2 to "Poor",
        3 to "Below Average",
        4 to "Average",
        5 to "Above Average",
        6 to "Good",
        7 to "Very Good",
        8 to "Excellent",
        9 to "Exceptional",
        10 to "Perfect"
    )
    return ratingsMap[rating] ?: "N/A"
}
fun String?.toBooleanOrFalse(): Boolean {
    return this.equals("TRUE", ignoreCase = true)
}