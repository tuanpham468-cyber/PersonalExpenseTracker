package com.example.personalexpensetracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.usecase.DeleteTransaction
import com.example.personalexpensetracker.domain.usecase.GetAllTransactions
import com.example.personalexpensetracker.domain.usecase.GetTransactionById
import com.example.personalexpensetracker.domain.usecase.InsertTransaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllTransactions: GetAllTransactions,
    private val insertTransaction: InsertTransaction,
    private val deleteTransaction: DeleteTransaction,
    private val getTransactionById: GetTransactionById
) : ViewModel() {

    val transactions = getAllTransactions().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val totalIncome = transactions.map { list ->
        list.filter { it.type == "income" }.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalExpense = transactions.map { list ->
        list.filter { it.type == "expense" }.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            insertTransaction.invoke(transaction)
        }
    }

    val expenseDistribution = transactions.map { list ->
        list.filter { it.type == "expense" }
            .groupBy { it.category ?: "Other" }
            .mapValues { entry -> entry.value.sumOf { it.amount } }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

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