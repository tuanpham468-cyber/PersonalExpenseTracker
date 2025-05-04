package com.example.personalexpensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.personalexpensetracker.presentation.home.HomeScreen
import com.example.personalexpensetracker.presentation.statistics.StatisticsScreen
import com.example.personalexpensetracker.presentation.transaction.add.AddTransactionScreen
import com.example.personalexpensetracker.presentation.transaction.edit.EditTransactionScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Statistics : Screen("statistics")
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
        composable(Screen.Statistics.route) {
            StatisticsScreen()
        }
        composable(Screen.AddTransaction.route) {
            AddTransactionScreen { navController.popBackStack() }
        }
        composable(Screen.EditTransaction.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            EditTransactionScreen(transactionId = id) { navController.popBackStack() }
        }
    }
}