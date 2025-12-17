package com.kaku.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Home : Route

    @Serializable
    data object Restful : Route

    @Serializable
    data object Graphql : Route
}
