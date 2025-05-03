package com.example.personalexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.personalexpensetracker.presentation.navigation.AppNavGraph
import com.example.personalexpensetracker.presentation.theme.PersonalExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            PersonalExpenseTrackerTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}