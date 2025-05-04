package com.example.personalexpensetracker.presentation.transaction.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.personalexpensetracker.presentation.transaction.common.DatePickerField
import com.example.personalexpensetracker.presentation.transaction.common.DropdownSelector
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = state.amount,
            onValueChange = { viewModel.onAmountChange(it) },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "Type",
            options = listOf("Income", "Expense"),
            selectedOption = state.type,
            onOptionSelected = { viewModel.onTypeChange(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "Category",
            options = state.availableCategories,
            selectedOption = state.category,
            onOptionSelected = { viewModel.onCategoryChange(it) },
            enabled = state.availableCategories.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onDescriptionChange(it) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DatePickerField(
            label = "Date",
            selectedDate = state.date,
            onDateChange = { viewModel.onDateChange(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.onAddTransaction { onNavigateBack() }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add")
        }
    }
}