package com.example.personalexpensetracker.presentation.transaction.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TransactionUiState(
    val id: Int = 0,
    val amount: String = "",
    val type: String = "",
    val category: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val availableCategories: List<String> = emptyList(),
    val isLoading: Boolean = false
)

abstract class BaseTransactionViewModel : ViewModel() {
    protected val _uiState = MutableStateFlow(TransactionUiState())
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    fun onAmountChange(amount: String) {
        _uiState.update { it.copy(amount = amount) }
    }

    fun onTypeChange(type: String) {
        getCategoryListForType(type)
        _uiState.update { it.copy(type = type) }
    }

    fun onCategoryChange(category: String) {
        _uiState.update { it.copy(category = category) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDateChange(date: Long) {
        _uiState.update { it.copy(date = date) }
    }

    fun getCategoryListForType(type: String) {
        val categories = when (type) {
            "Income" -> listOf("Salary", "Reward", "Other")
            "Expense" -> listOf("Food", "Shopping", "Transport", "Entertainment", "Health", "House", "Other")
            else -> emptyList()
        }
        _uiState.update { it.copy(availableCategories = categories, category = categories.firstOrNull() ?: "") }
    }
}