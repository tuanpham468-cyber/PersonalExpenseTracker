package com.example.personalexpensetracker.data.repository

import com.example.personalexpensetracker.data.local.dao.TransactionDao
import com.example.personalexpensetracker.data.local.mapper.toDomain
import com.example.personalexpensetracker.data.local.mapper.toEntity
import com.example.personalexpensetracker.domain.model.Transaction
import com.example.personalexpensetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val dao: TransactionDao) : TransactionRepository {
    override fun getAll(): Flow<List<Transaction>> = dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun insert(transaction: Transaction) = dao.insert(transaction.toEntity())

    override suspend fun delete(transaction: Transaction) = dao.delete(transaction.toEntity())

    override suspend fun getById(id: Int): Transaction? {
        return dao.getById(id)?.toDomain()
    }
}