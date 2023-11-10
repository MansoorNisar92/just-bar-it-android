package com.android.app.justbarit.domain.model

data class Category(
    var categoryName: String,
    var categoryRes: Int,
    var selected:Boolean,
    var categoryType: CategoryType
)

sealed class CategoryType {
    data object Food : CategoryType()
    data object Jazz : CategoryType()
    data object Karaoke : CategoryType()
    data object Sport : CategoryType()

}
