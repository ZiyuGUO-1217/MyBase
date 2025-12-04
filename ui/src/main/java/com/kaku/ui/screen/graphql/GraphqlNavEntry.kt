package com.kaku.ui.screen.graphql

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import com.kaku.ui.Route

@Composable
fun EntryProviderScope<Route>.graphqlNavEntry() {
    entry<Route.Graphql> {
        GraphqlScreen()
    }
}
