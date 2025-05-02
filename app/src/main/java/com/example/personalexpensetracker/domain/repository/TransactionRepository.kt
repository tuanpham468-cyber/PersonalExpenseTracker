package com.example.personalexpensetracker.domain.repository

import com.example.personalexpensetracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAll(): Flow<List<Transaction>>
    suspend fun insert(transaction: Transaction)
    suspend fun delete(transaction: Transaction)
    suspend fun getById(id: Int): Transaction?
}