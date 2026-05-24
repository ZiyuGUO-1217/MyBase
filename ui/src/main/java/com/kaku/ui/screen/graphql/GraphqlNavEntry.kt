package com.kaku.ui.screen.graphql

import androidx.navigation3.runtime.EntryProviderScope
import com.kaku.ui.Route

fun EntryProviderScope<Route>.graphqlNavEntry() {
    entry<Route.Graphql> {
        GraphqlScreen()
    }
}
