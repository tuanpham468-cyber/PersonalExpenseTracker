package com.example.personalexpensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val type: String, //income | expense
    val category: String,
    val description: String,
    val date: Long
)