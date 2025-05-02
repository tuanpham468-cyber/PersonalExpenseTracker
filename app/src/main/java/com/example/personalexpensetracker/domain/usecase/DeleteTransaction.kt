package com.example.personalexpensetracker.domain.usecase

import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.repository.TransactionRepository

class DeleteTransaction(private val repo: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction) {
        repo.delete(transaction)
    }
}