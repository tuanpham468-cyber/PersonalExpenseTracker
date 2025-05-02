package com.example.personalexpensetracker.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpenseBarChart(
    viewModel: HomeViewModel = koinViewModel()
) {
    val data by viewModel.expenseDistribution.collectAsState()

    if (data.isEmpty()) {
        Text("No expense data")
        return
    }

    val entries = data.entries.mapIndexed { index, entry ->

    }
}