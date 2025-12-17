package com.kaku.ui.screen.home

import androidx.navigation3.runtime.EntryProviderScope
import com.kaku.ui.Route
import com.kaku.ui.Route.Home

fun EntryProviderScope<Route>.homeNavEntry() {
    entry<Home> {
        HomeScreen()
    }
}
