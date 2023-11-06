package com.teddy.booksearch.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.teddy.booksearch.SearchRoute
import com.teddy.booksearch.SearchScreen

fun NavGraphBuilder.searchScreen() {
    composable(route = "search") {
        SearchRoute()
    }
}