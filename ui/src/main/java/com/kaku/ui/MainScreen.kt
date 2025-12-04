package com.kaku.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.kaku.ui.screen.graphql.graphqlNavEntry
import com.kaku.ui.screen.home.homeNavEntry
import com.kaku.ui.screen.restful.restfulNavEntry

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val backStack by viewModel.backStack.collectAsStateWithLifecycle()

    val onNavigateTo: (Route) -> Unit = viewModel::onNavigateTo
    val onBack: () -> Unit = viewModel::onBack

    Column(modifier = modifier) {
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.weight(1f),
            onBack = onBack,
            entryDecorators = listOf(
                // Add the default decorators for managing scenes and saving state
                rememberSaveableStateHolderNavEntryDecorator(),
                // Add the view model store decorator, scoping ViewModels to NavEntrys instead of Activities/Fragments
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                homeNavEntry()
                restfulNavEntry()
                graphqlNavEntry()
            }
        )
        BottomNavigationBar(
            currentRoute = backStack.last(),
            onNavigateTo = onNavigateTo
        )
    }
}

@Composable
private fun BottomNavigationBar(
    currentRoute: Route,
    onNavigateTo: (Route) -> Unit,
) {
    NavigationBar {
        NavBarItems.entries.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigateTo(item.route) },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (currentRoute == item.route) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            }
                        ),
                        contentDescription = item.title
                    )
                },
            )
        }
    }
}
