package com.example.personalexpensetracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personalexpensetracker.data.local.dao.TransactionDao
import com.example.personalexpensetracker.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}