package com.kaku.ui.screen.restful

import androidx.navigation3.runtime.EntryProviderScope
import com.kaku.ui.Route

fun EntryProviderScope<Route>.restfulNavEntry() {
    entry<Route.Restful> {
        RestfulScreen()
    }
}
