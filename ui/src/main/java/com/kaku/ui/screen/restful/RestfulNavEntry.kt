package com.kaku.ui.screen.restful

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import com.kaku.ui.Route

@Composable
fun EntryProviderScope<Route>.restfulNavEntry() {
    entry<Route.Restful> {
        RestfulScreen()
    }
}
