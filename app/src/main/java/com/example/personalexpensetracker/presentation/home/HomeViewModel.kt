package com.example.personalexpensetracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.model.TransactionFilter
import com.example.personalexpensetracker.domain.usecase.GetAllTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : ViewModel() {

    private val _filter = MutableStateFlow(TransactionFilter.ALL)
    val filter = _filter.asStateFlow()

    val transactions: StateFlow<List<Transaction>> = combine(
        _filter, getAllTransactionsUseCase()
    ) { filter, allTransactions ->
        when (filter) {
            TransactionFilter.ALL -> allTransactions
            TransactionFilter.INCOME -> allTransactions.filter { it.type == "Income" }
            TransactionFilter.EXPENSE -> allTransactions.filter { it.type == "Expense" }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onFilterSelected(filter: TransactionFilter) {
        _filter.value = filter
    }
}