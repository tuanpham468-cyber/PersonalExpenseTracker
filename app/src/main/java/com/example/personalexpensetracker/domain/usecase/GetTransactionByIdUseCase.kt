package com.example.personalexpensetracker.domain.usecase

import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.repository.TransactionRepository

class GetTransactionByIdUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(id: Int): Transaction? {
        return repository.getById(id)
    }
}