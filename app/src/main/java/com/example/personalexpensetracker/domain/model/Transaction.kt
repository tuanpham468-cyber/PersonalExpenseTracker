package com.example.personalexpensetracker.domain.model

data class Transaction(
    val id: Int = 0,
    val amount: Double,
    val type: String, //income | expense
    val category: String,
    val description: String,
    val date: Long
)