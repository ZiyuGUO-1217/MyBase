package com.kaku.ui

import androidx.annotation.DrawableRes

enum class NavBarItems(
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val route: Route
) {
    HOME(
        title = "Home",
        selectedIcon = R.drawable.ic_main_home_select,
        unselectedIcon = R.drawable.ic_main_home,
        route = Route.Home
    ),
    RESTFUL(
        title = "RESTful",
        selectedIcon = R.drawable.ic_main_restful_select,
        unselectedIcon = R.drawable.ic_main_restful,
        route = Route.Restful
    ),
    GRAPHQL(
        title = "GraphQL",
        selectedIcon = R.drawable.ic_main_graphql_select,
        unselectedIcon = R.drawable.ic_main_graphql,
        route = Route.Graphql
    );
}
