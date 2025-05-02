package com.example.personalexpensetracker.presentation.edit_transaction

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.presentation.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditTransactionScreen(
    id: Int?,
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    var transaction by remember { mutableStateOf<Transaction?>(null) }

    LaunchedEffect(id) {
        id?.let {
            viewModel.getTransaction(it) { result ->
                transaction = result
            }
        }
    }

    transaction?.let {
        var amount by remember { mutableStateOf(it.amount.toString()) }
        var description by remember { mutableStateOf(it.description) }
        var type by remember { mutableStateOf(it.type) }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Edit Transaction", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })

            Row {
                RadioButton(selected = type == "expense", onClick = { type = "expense" })
                Text("Expense")
                Spacer(Modifier.height(8.dp))
                RadioButton(selected = type == "income", onClick = { type = "income" })
                Text("Income")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.updateTransaction(
                    it.copy(
                        amount = amount.toDoubleOrNull() ?: it.amount,
                        type = type,
                        description = description

                    )
                )
                navController.popBackStack()
            }) {
                Text("Update")
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}