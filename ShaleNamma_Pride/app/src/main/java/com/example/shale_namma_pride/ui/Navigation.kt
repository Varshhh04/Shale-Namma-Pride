package com.example.shale_namma_pride.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object Login : Screen
    @Serializable
    data object Schools : Screen
    @Serializable
    data object Attendance : Screen
    @Serializable
    data object Projects : Screen
    @Serializable
    data object Meals : Screen
    @Serializable
    data object MealsAdmin : Screen
    @Serializable
    data object Gallery : Screen
    @Serializable
    data object Camera : Screen
    @Serializable
    data object Feedback : Screen
    @Serializable
    data object FeedbackAdmin : Screen
}
