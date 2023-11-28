package com.android.app.justbarit.presentation.common.customviews.bottomnavigation.annotations

import androidx.annotation.Dimension

/**
 * Denotes that an integer parameter, field or method return value is expected
 * to represent a device independent pixel dimension.
 */
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.VALUE_PARAMETER,
  AnnotationTarget.FIELD,
  AnnotationTarget.LOCAL_VARIABLE
)
@Dimension(unit = Dimension.DP)
internal annotation class Dp
