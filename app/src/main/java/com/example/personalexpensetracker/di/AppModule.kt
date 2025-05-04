package com.example.personalexpensetracker.di

import androidx.room.Room
import com.example.personalexpensetracker.data.local.database.ExpenseDatabase
import com.example.personalexpensetracker.data.repository.TransactionRepositoryImpl
import com.example.personalexpensetracker.domain.repository.TransactionRepository
import com.example.personalexpensetracker.domain.usecase.AddTransactionUseCase
import com.example.personalexpensetracker.domain.usecase.DeleteTransactionUseCase
import com.example.personalexpensetracker.domain.usecase.EditTransactionUseCase
import com.example.personalexpensetracker.domain.usecase.GetAllTransactionsUseCase
import com.example.personalexpensetracker.domain.usecase.GetTransactionByIdUseCase
import com.example.personalexpensetracker.presentation.home.HomeViewModel
import com.example.personalexpensetracker.presentation.statistics.StatisticsViewModel
import com.example.personalexpensetracker.presentation.transaction.add.AddTransactionViewModel
import com.example.personalexpensetracker.presentation.transaction.edit.EditTransactionViewModel
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
    factory { GetAllTransactionsUseCase(get()) }
    factory { AddTransactionUseCase(get()) }
    factory { DeleteTransactionUseCase(get()) }
    factory { GetTransactionByIdUseCase(get()) }
    factory { EditTransactionUseCase(get()) }

    // ViewModel
    viewModel { HomeViewModel(getAllTransactionsUseCase = get()) }
    viewModel {
        EditTransactionViewModel(
            getTransactionByIdUseCase = get(),
            editTransactionUseCase = get()
        )
    }
    viewModel { AddTransactionViewModel(addTransactionUseCase = get()) }
    viewModel { StatisticsViewModel(getAllTransactionsUseCase = get()) }
}