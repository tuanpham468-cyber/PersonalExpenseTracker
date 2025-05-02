package com.example.personalexpensetracker.presentation.add_transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.presentation.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTransactionScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("expense") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") }
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Row {
            RadioButton(
                selected = type == "expense",
                onClick = { type = "expense" }
            )
            Text("Expense")
            Spacer(Modifier.width(8.dp))
            RadioButton(
                selected = type == "income",
                onClick = { type = "income" }
            )
            Text("Income")
        }

        Button(onClick = {
            viewModel.addTransaction(
                Transaction(
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    type = type,
                    description = description,
                    date = System.currentTimeMillis()
                )
            )
            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}