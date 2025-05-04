package com.example.personalexpensetracker.presentation.transaction.add

import androidx.lifecycle.viewModelScope
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.usecase.AddTransactionUseCase
import com.example.personalexpensetracker.presentation.transaction.common.BaseTransactionViewModel
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val addTransactionUseCase: AddTransactionUseCase
) : BaseTransactionViewModel() {
    fun onAddTransaction(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = uiState.value
            val transaction = Transaction(
                amount = state.amount.toDoubleOrNull() ?: 0.0,
                type = state.type,
                category = state.category,
                description = state.description,
                date = state.date
            )
            addTransactionUseCase(transaction)
            onSuccess()
        }
    }
}