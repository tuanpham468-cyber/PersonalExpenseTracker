package com.example.personalexpensetracker.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.personalexpensetracker.presentation.navigation.Screen
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val transactions by viewModel.transactions.collectAsState()
    val income by viewModel.totalIncome.collectAsState()
    val expense by viewModel.totalExpense.collectAsState()

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
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Income", color = Color.Green, fontWeight = FontWeight.Bold)
                        Text("+${income}", color = Color.Green)
                    }
                    Column {
                        Text("Total Expense", color = Color.Red, fontWeight = FontWeight.Bold)
                        Text("-${expense}", color = Color.Red)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            //Danh sach giao dich
            LazyColumn {
                items(transactions) { transition ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate(Screen.EditTransaction.createRoute(transition.id))
                            }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(transition.description, fontWeight = FontWeight.Bold)
                            Text(
                                text = (if (transition.type == "income") "+ " else "- ") + transition.amount,
                                color = if (transition.type == "income") Color.Green else Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}