package com.example.personalexpensetracker.data.local.mapper

import com.example.personalexpensetracker.data.local.entity.TransactionEntity
import com.example.personalexpensetracker.domain.model.Transaction

fun TransactionEntity.toDomain() = Transaction(id, amount, type, description, date)

fun Transaction.toEntity() = TransactionEntity(id, amount, type, description, date)