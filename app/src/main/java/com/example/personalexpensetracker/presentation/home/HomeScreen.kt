package com.example.personalexpensetracker.presentation.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.model.TransactionFilter
import com.example.personalexpensetracker.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    Log.d("HomeScreen", "Start screen")

    val filter by viewModel.filter.collectAsStateWithLifecycle()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()

    val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == "Expense" }.sumOf { it.amount }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddTransaction.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            //Tong tien
            Log.d("HomeScreen", "Start calculate total")
            Text("Tổng thu: $totalIncome", style = MaterialTheme.typography.titleMedium)
            Text("Tổng chi: $totalExpense", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(16.dp))

            FilterSelector(
                selectedFilter = filter,
                onFilterChange = viewModel::onFilterSelected
            )

            Spacer(Modifier.height(16.dp))

            //Danh sach giao dich
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction) {
                        navController.navigate(Screen.EditTransaction.createRoute(transaction.id))
                    }
                }
            }
        }
    }

    Log.d("HomeScreen", "End screen")
}

@Composable
fun TransactionItem(transaction: Transaction, onItemClick: () -> Unit) {
    Log.d("HomeScreen", "Start transaction item")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onItemClick.invoke()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (transaction.type == "Income") MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("So tien: ${transaction.amount}")
            Text("Loai: ${transaction.type}")
            Text("Danh muc: ${transaction.category}")
            if (transaction.description.isNotBlank()) {
                Text("Mo ta: ${transaction.description}")
            }
        }
    }
    Log.d("HomeScreen", "End transaction item")
}

@Composable
fun FilterSelector(
    selectedFilter: TransactionFilter,
    onFilterChange: (TransactionFilter) -> Unit
) {
    Log.d("HomeScreen", "Start filter")
    val filters = listOf(
        TransactionFilter.ALL to "Tất cả",
        TransactionFilter.INCOME to "Chỉ thu",
        TransactionFilter.EXPENSE to "Chỉ chi"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        filters.forEach { (filter, label) ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterChange(filter) },
                label = { Text(label) }
            )
        }
    }
    Log.d("HomeScreen", "End filter")
}