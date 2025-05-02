package com.example.personalexpensetracker.di

import androidx.room.Room
import com.example.personalexpensetracker.data.local.database.ExpenseDatabase
import com.example.personalexpensetracker.data.repository.TransactionRepositoryImpl
import com.example.personalexpensetracker.domain.repository.TransactionRepository
import com.example.personalexpensetracker.domain.usecase.DeleteTransaction
import com.example.personalexpensetracker.domain.usecase.GetAllTransactions
import com.example.personalexpensetracker.domain.usecase.GetTransactionById
import com.example.personalexpensetracker.domain.usecase.InsertTransaction
import com.example.personalexpensetracker.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = ExpenseDatabase::class.java,
            name = "expense_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<ExpenseDatabase>().transactionDao() }

    single<TransactionRepository> { TransactionRepositoryImpl(get()) }

    // UseCase
    factory { GetAllTransactions(get()) }

    factory { InsertTransaction(get()) }

    factory { DeleteTransaction(get()) }

    factory { GetTransactionById(get()) }

    // ViewModel
    viewModel {
        HomeViewModel(
            getAllTransactions = get(),
            insertTransaction = get(),
            deleteTransaction = get(),
            getTransactionById = get()
        )
    }
}