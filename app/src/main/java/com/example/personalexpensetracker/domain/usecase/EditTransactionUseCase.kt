package com.example.personalexpensetracker.domain.usecase

import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.repository.TransactionRepository

class EditTransactionUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction) {
        repository.updateTransaction(transaction)
    }
}