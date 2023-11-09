package com.teddy.booksearch

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.teddy.booksearch.navigation.detailScreen
import com.teddy.booksearch.navigation.searchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        searchScreen(navController)
        detailScreen()
    }
}
