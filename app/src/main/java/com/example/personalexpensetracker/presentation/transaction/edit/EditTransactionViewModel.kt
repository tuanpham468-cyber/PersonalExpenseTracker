package com.example.personalexpensetracker.presentation.transaction.edit

import androidx.lifecycle.viewModelScope
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.usecase.EditTransactionUseCase
import com.example.personalexpensetracker.domain.usecase.GetTransactionByIdUseCase
import com.example.personalexpensetracker.presentation.transaction.common.BaseTransactionViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditTransactionViewModel(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val editTransactionUseCase: EditTransactionUseCase
) : BaseTransactionViewModel() {
    fun loadTransaction(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val transaction = getTransactionByIdUseCase(id)
            getCategoryListForType(type = transaction?.type ?: "Expense")
            _uiState.update {
                it.copy(
                    isLoading = false,
                    id = transaction?.id ?: 0,
                    amount = transaction?.amount?.toString() ?: "",
                    type = transaction?.type ?: "Expense",
                    category = transaction?.category ?: "",
                    description = transaction?.description ?: "",
                    date = transaction?.date ?: System.currentTimeMillis()
                )
            }
        }
    }

    fun onUpdate(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val transaction = Transaction(
                id = state.id,
                amount = state.amount.toDoubleOrNull() ?: 0.0,
                type = state.type,
                category = state.category,
                description = state.description,
                date = state.date
            )
            editTransactionUseCase(transaction)
            onSuccess()
        }
    }
}