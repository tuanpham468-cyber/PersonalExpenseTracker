package com.example.personalexpensetracker.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalexpensetracker.domain.usecase.GetAllTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StatisticsViewModel(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(StatisticsUiState())
    val uiState: StateFlow<StatisticsUiState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
           getAllTransactionsUseCase()
               .collect { transactions ->
                   //Group by month-year
                   val grouped = transactions.groupBy {
                       val calendar = Calendar.getInstance().apply { timeInMillis = it.date }
                       "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                   }

                   val months = grouped.keys.sortedBy { monthYear ->
                       SimpleDateFormat("MM/yyyy", Locale.getDefault()).parse(monthYear)?.time ?: 0
                   }

                   val incomeList = months.map { month ->
                       grouped[month]
                           ?.filter { it.type == "Income" }
                           ?.sumOf { it.amount }
                           ?.toFloat() ?: 0f
                   }

                   val expenseList = months.map { month ->
                       grouped[month]
                           ?.filter { it.type == "Expense" }
                           ?.sumOf { it.amount }
                           ?.toFloat() ?: 0f
                   }

                   _uiState.value = StatisticsUiState(
                       incomePerMonth = incomeList,
                       expensePerMonth = expenseList,
                       labels = months
                   )
               }
        }
    }
}

data class StatisticsUiState(
    val incomePerMonth: List<Float> = emptyList(),
    val expensePerMonth: List<Float> = emptyList(),
    val labels: List<String> = emptyList()
)