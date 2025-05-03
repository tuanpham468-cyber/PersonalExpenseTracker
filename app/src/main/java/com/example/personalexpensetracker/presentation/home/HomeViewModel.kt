package com.example.personalexpensetracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.model.TransactionFilter
import com.example.personalexpensetracker.domain.usecase.DeleteTransaction
import com.example.personalexpensetracker.domain.usecase.GetAllTransactions
import com.example.personalexpensetracker.domain.usecase.GetTransactionById
import com.example.personalexpensetracker.domain.usecase.InsertTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllTransactions: GetAllTransactions,
    private val insertTransaction: InsertTransaction,
    private val deleteTransaction: DeleteTransaction,
    private val getTransactionById: GetTransactionById
) : ViewModel() {

    private val _filter = MutableStateFlow(TransactionFilter.ALL)
    val filter = _filter.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = combine(
        _filter, getAllTransactions()
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

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            insertTransaction.invoke(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            deleteTransaction.invoke(transaction)
        }
    }

    fun getTransaction(id: Int, onResult: (Transaction?) -> Unit) {
        viewModelScope.launch {
            val transaction = getTransactionById(id)
            onResult(transaction)
        }
    }

    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            insertTransaction(transaction) //update = insert with same id
        }
    }
}