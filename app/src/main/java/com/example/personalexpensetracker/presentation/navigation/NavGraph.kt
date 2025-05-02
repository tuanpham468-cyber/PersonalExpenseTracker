package com.example.personalexpensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.personalexpensetracker.presentation.add_transaction.AddTransactionScreen
import com.example.personalexpensetracker.presentation.edit_transaction.EditTransactionScreen
import com.example.personalexpensetracker.presentation.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddTransaction : Screen("add")
    object EditTransaction : Screen("edit/{id}") {
        fun createRoute(id: Int) = "edit/$id"
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(navController = navController)
        }
        composable(Screen.EditTransaction.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            EditTransactionScreen(id = id, navController = navController)
        }
    }
}