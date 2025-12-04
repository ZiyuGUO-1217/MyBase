package com.kaku.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import com.kaku.ui.Route
import com.kaku.ui.Route.Home

@Composable
fun EntryProviderScope<Route>.homeNavEntry() {
    entry<Home> {
        HomeScreen()
    }
}
