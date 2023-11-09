package com.teddy.booksearch.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.teddy.booksearch.detail.DetailRoute
import com.teddy.booksearch.search.SearchRoute

fun NavGraphBuilder.searchScreen(navController: NavController) {
    composable(route = "search") {
        SearchRoute(
            onBookClicked = { isbn13: String ->
                navController.navigate("detail/$isbn13")
            }
        )
    }
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = "detail/{isbn13}",
        arguments = listOf(
            navArgument("isbn13") {
                type = NavType.StringType
            }
        )
    ) {
        DetailRoute()
    }
}